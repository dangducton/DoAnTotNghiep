package com.dangducton.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class MainController {

	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;

	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;

	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;

	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;

	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@GetMapping("/")
	public String main(Model model, HttpServletRequest request, @ModelAttribute("status") String status) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			Nguoidung ng = new Nguoidung();
			ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
			model.addAttribute("tenNguoiDung", ng);
			List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
			double tongSoTien = 0;
			for (Chitietdonhang ct1 : list1) {
				tongSoTien = tongSoTien + (ct1.getGiasanpham() * ct1.getSoluong());
			}
			model.addAttribute("tongSoTien", tongSoTien);
			model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
			model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
			model.addAttribute("module", "Trang chủ");
			model.addAttribute("title", "Trang chủ");
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("listSanPham", anhSanPhamServiceImpl.findAllSanPhamMuaNhieuNhat());
			model.addAttribute("listSanPhamMoiNhat", anhSanPhamServiceImpl.findAllSanPhamMuaMoiNhat());
			model.addAttribute("status", status);
			return "frontend/index";
		} else {
			model.addAttribute("module", "Trang chủ");
			model.addAttribute("title", "Trang chủ");
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("listSanPham", anhSanPhamServiceImpl.findAllSanPhamMuaNhieuNhat());
			model.addAttribute("listSanPhamMoiNhat", anhSanPhamServiceImpl.findAllSanPhamMuaMoiNhat());
			model.addAttribute("status", status);
			return "frontend/index";
		}
	}

	@GetMapping(value = { "/timKiemSanPhamTheoTen" })
	public String sanPhamTheoGia(Model model, @ModelAttribute("status") String status,
			@RequestParam(name = "tensanpham") String tenSanPham, @PageableDefault(size = 12) Pageable pageable,
			HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("module", "Shop");
			model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByTenSanPhamActive(tenSanPham, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
					"/timKiemSanPhamTheoGia/" + "?tensanpham=" + tenSanPham);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			Nguoidung ng = new Nguoidung();
			ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
			model.addAttribute("tenNguoiDung", ng);
			List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
			double tongSoTien = 0;
			for (Chitietdonhang ct1 : list1) {
				tongSoTien = tongSoTien + (ct1.getGiasanpham() * ct1.getSoluong());
			}
			model.addAttribute("tongSoTien", tongSoTien);
			model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
			model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
			return "frontend/danhsachsanpham";
		} else {
			model.addAttribute("module", "Shop");
			model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByTenSanPhamActive(tenSanPham, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
					"/timKiemSanPhamTheoGia/" + "?tensanpham=" + tenSanPham);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		}
	}
}
