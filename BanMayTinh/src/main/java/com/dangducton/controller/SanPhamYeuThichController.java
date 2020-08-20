package com.dangducton.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.SanPhamYeuThichDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Sanpham;
import com.dangducton.entities.Sanphamyeuthich;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;

@Controller
public class SanPhamYeuThichController {
	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;

	@GetMapping("/banhang/sanphamyeuthich")
	public String danhSachSanpham(Model model, @PageableDefault(size = 12) Pageable pageable,
			@ModelAttribute("status") String status, HttpServletRequest request) {
		model.addAttribute("title", "Sản phẩm yêu thích");
		model.addAttribute("status", status);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		List<Sanphamyeuthich> list = sanPhamYeuThichServiceImpl.findAllByNguoiDung(ng.getId());
		List<SanPhamYeuThichDTO> listGioHang = new ArrayList<SanPhamYeuThichDTO>();
		List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
		for (Sanphamyeuthich ct : list) {
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			Sanpham asp1 = sanPhamServiceImpl.findByIdSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new SanPhamYeuThichDTO(asp1.getTen(), asp1.getId(), asp.getUrlAnh(), asp1.getGiaban(),
					asp1.getGiamgia(),ct.getId()));
		}
		double tongSoTien = 0;
		for (Chitietdonhang ct1 : list1) {
			tongSoTien = tongSoTien + (ct1.getGiasanpham() * ct1.getSoluong());
		}
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("tongSoSanPhamYeuThich",sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		model.addAttribute("listGioHang", listGioHang);
		return "frontend/sanphamyeuthich";
	}

	@GetMapping("/banhang/themmotsanphamvaosanphamyeuthich/{id}")
	public String capNhatGioHang(@PathVariable("id") int id, RedirectAttributes ra, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		int idNguoiDung = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName()).getId();
		Sanphamyeuthich ctdh1 = sanPhamYeuThichServiceImpl.findAllBySanPhamYeuThich(idNguoiDung, id);
		if (ctdh1 != null) {
			ra.addFlashAttribute("status", "Bạn đã thêm sản phẩm này");
			return "redirect:/banhang/sanphamyeuthich";
		} else {
			Sanphamyeuthich spyt = new Sanphamyeuthich();
			Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
			spyt.setIdNguoidung(ng);
			spyt.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(id));
			Date date = new Date();
			spyt.setNgaytao(date);
			spyt.setStatus(1);
			ra.addFlashAttribute("status", "Thêm thành công");
			sanPhamYeuThichServiceImpl.save(spyt);
			return "redirect:/banhang/sanphamyeuthich";
		}
	}
	@PostMapping("/banhang/deletesanphamyeuthich")
	public String deleteSanPhamGioHang(@RequestParam(name = "idSanPhamYeuThich") Integer id,
			RedirectAttributes ra, HttpServletRequest request) {
		sanPhamYeuThichServiceImpl.delete(id);
		ra.addFlashAttribute("status", "Xóa thành công");
		return "redirect:/banhang/sanphamyeuthich";

	}
}

