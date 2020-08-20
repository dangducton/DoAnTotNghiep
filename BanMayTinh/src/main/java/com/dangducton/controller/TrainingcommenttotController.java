package com.dangducton.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.TonKhoDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Trainingcommenttot;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.TrainingcommenttotServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class TrainingcommenttotController {
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	private TrainingcommenttotServiceImpl trainingcommenttotServiceImpl;

	@GetMapping(value = { "/admin/trainingcommenttot" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Comment tốt");
		model.addAttribute("status", status);
		Page<Trainingcommenttot> danTocPage = trainingcommenttotServiceImpl.findAllDanhMuc(pageable);
		Pagination<Trainingcommenttot> page = new Pagination<Trainingcommenttot>(danTocPage, "/admin/trainingcommenttot");
		model.addAttribute("listDanhMuc", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("title", "Trang chủ");
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Donhang> dh = donHangServiceImpl.findByListDonHangMoi();
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive1();
		List<TonKhoDTO> listTonKho1 = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage1) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				if (tongNhap - tongBan < 5) {
					int tonKho = tongNhap - tongBan;
					listTonKho1.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan,
							tonKho, asp.getIdSanpham().getId()));
				} else {

				}
			}
		}
		model.addAttribute("soLuongDonHang1", dh.size());
		model.addAttribute("listDonHang1", dh);
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());

		return "admin/trainingcommenttot";
	}

	@GetMapping("/admin/findonetrainingcommenttot")
	@ResponseBody
	public Optional<Trainingcommenttot> findById(Integer id) {
		return trainingcommenttotServiceImpl.findById(id);
	}

	@GetMapping(path = "/admin/deletecomenttot")
	public String updateNotActivate( Integer id, Trainingcommenttot dt, RedirectAttributes ra) {
		
		trainingcommenttotServiceImpl.delete(id);
		ra.addFlashAttribute("status", "Xóa thành công");
		return "redirect:/admin/trainingcommenttot";
	}
//
//	@GetMapping(path = "/admin/updateActivateDanToc/{id}")
//	public String updateActivate(@PathVariable("id") Integer id, Danhmuc dt, RedirectAttributes ra) {
//		dt.setTen(danhMucServiceImpl.findById(id).get().getTen());
//		dt.setNgaytao(danhMucServiceImpl.findById(id).get().getNgaytao());
//		dt.setStatus(1);
//		danhMucServiceImpl.save(dt);
//		ra.addFlashAttribute("status", "Update thành công");
//		return "redirect:/admin/danhmuc";
//	}

	@PostMapping(value = "/admin/addtrainingcommenttot")
	public String addPhongBan(@RequestParam(name = "tendanhmuc") String tenDanhMuc, Model model,
			RedirectAttributes ra) {
		List<Trainingcommenttot> listDanToc = trainingcommenttotServiceImpl.findAllByTenDanhMuc(tenDanhMuc);
		if (listDanToc.size() >= 1) {
			ra.addFlashAttribute("status", "Tên comment đã tồn tại");
			return "redirect:/admin/trainingcommenttot";
		} else {
			Date date = new Date();
			Trainingcommenttot dt = new Trainingcommenttot();
			dt.setNgaytao(date);
			dt.setTen(tenDanhMuc);
			dt.setStatus(1);
			trainingcommenttotServiceImpl.save(dt);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/trainingcommenttot";
		}
	}

	@PostMapping(value = "/admin/updatetrainingcommenttot")
	public String updateChucDanh(@RequestParam(name = "id") Integer id,
			@RequestParam(name = "tendanhmuc") String tenDanhMuc, @RequestParam(name = "ngaytao") String ngayTao,
			@RequestParam(name = "status") Integer status, Model model, RedirectAttributes ra) {
		Trainingcommenttot dt = new Trainingcommenttot();
		Date ngayTaoParse = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ngayTaoParse = sdf.parse(ngayTao);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dt.setId(id);
		dt.setNgaytao(ngayTaoParse);
		dt.setTen(tenDanhMuc);
		dt.setStatus(1);
		trainingcommenttotServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/trainingcommenttot";
	}
}
