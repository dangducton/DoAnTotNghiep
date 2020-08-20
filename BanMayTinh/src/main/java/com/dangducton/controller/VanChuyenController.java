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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.TonKhoDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Vanchuyen;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.VanChuyenServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class VanChuyenController {
	@Autowired
	private VanChuyenServiceImpl VanChuyenServiceImpl;
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

	@GetMapping(value = { "/admin/vanchuyen" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Vận chuyển");
		model.addAttribute("status", status);
		Page<Vanchuyen> danTocPage = VanChuyenServiceImpl.findAllDanhMuc(pageable);
		Pagination<Vanchuyen> page = new Pagination<Vanchuyen>(danTocPage, "/admin/danhmuc");
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

		return "admin/vanchuyen";
	}

	@GetMapping("/admin/findonevanchuyen")
	@ResponseBody
	public Optional<Vanchuyen> findById(Integer id) {
		return VanChuyenServiceImpl.findById(id);
	}

	@GetMapping(path = "/admin/updateNotActivateVanChuyen/{id}")
	public String updateNotActivate(@PathVariable("id") Integer id, Vanchuyen dt, RedirectAttributes ra) {
		dt.setTen(VanChuyenServiceImpl.findById(id).get().getTen());
		dt.setNgaytao(VanChuyenServiceImpl.findById(id).get().getNgaytao());
		dt.setStatus(0);
		VanChuyenServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/vanchuyen";
	}

	@GetMapping(path = "/admin/updateActivateVanChuyen/{id}")
	public String updateActivate(@PathVariable("id") Integer id, Vanchuyen dt, RedirectAttributes ra) {
		dt.setTen(VanChuyenServiceImpl.findById(id).get().getTen());
		dt.setNgaytao(VanChuyenServiceImpl.findById(id).get().getNgaytao());
		dt.setStatus(1);
		VanChuyenServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/vanchuyen";
	}

	@PostMapping(value = "/admin/addVanChuyen")
	public String addPhongBan(@RequestParam(name = "tendanhmuc") String tenDanhMuc, Model model,
			RedirectAttributes ra) {
		List<Vanchuyen> listDanToc = VanChuyenServiceImpl.findAllByTenDanhMuc(tenDanhMuc);
		if (listDanToc.size() >= 1) {
			ra.addFlashAttribute("status", "Tên danh mục đã tồn tại");
			return "redirect:/admin/danhmuc";
		} else {
			Date date = new Date();
			Vanchuyen dt = new Vanchuyen();
			dt.setNgaytao(date);
			dt.setTen(tenDanhMuc);
			dt.setStatus(1);
			VanChuyenServiceImpl.save(dt);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/vanchuyen";
		}
	}

	@PostMapping(value = "/admin/updateVanChuyen")
	public String updateChucDanh(@RequestParam(name = "id") Integer id,
			@RequestParam(name = "tendanhmuc") String tenDanhMuc, @RequestParam(name = "ngaytao") String ngayTao,
			@RequestParam(name = "status") Integer status, Model model, RedirectAttributes ra) {
		Vanchuyen dt = new Vanchuyen();
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
		VanChuyenServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/vanchuyen";
	}
}
