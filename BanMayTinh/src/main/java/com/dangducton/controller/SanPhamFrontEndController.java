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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Comment;
import com.dangducton.entities.Nguoidung;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhaCungCapServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class SanPhamFrontEndController {
	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	NhaCungCapServiceImpl nhaCungCapServiceImpl;
	@Autowired
	CommentServiceImpl commentServiceImpl;

	@GetMapping("/danhsachsanpham")
	public String danhSachSanpham(Model model, @PageableDefault(size = 9) Pageable pageable,
			HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("title", "Danh sách sản phẩm");
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
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllBySanPhamActive(pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/danhsachsanpham");
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);

			return "frontend/danhsachsanpham";
		} else {
			model.addAttribute("title", "Danh sách sản phẩm");

			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllBySanPhamActive(pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/danhsachsanpham");
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);

			return "frontend/danhsachsanpham";
		}
	}

	@GetMapping("/findsanphambyid/{id}")
	public String danhSachSanpham(@PathVariable("id") Integer id, Model model, HttpServletRequest request,@ModelAttribute("status") String status,
			@PageableDefault(size = 5) Pageable pageable) {
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
			Page<Comment> danTocPage = commentServiceImpl.findAllDanhMuc(id, pageable);
			Pagination<Comment> page = new Pagination<Comment>(danTocPage, "/findsanphambyid/" + id);
			model.addAttribute("comment", page.getContent());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
			model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
			model.addAttribute("module", "Shop");
			model.addAttribute("status", status);
			model.addAttribute("sanPham", sanPhamServiceImpl.findByIdSanPham(id));
			model.addAttribute("noiDung", sanPhamServiceImpl.findByIdSanPham(id).getMota());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("listSanPham", anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham());
			List<Anhsanpham> asp = anhSanPhamServiceImpl.findByIdAnhSanPhamIdSanPham(id);
			model.addAttribute("listAnh", asp);
			model.addAttribute("title", sanPhamServiceImpl.findByIdSanPham(id).getTen());
			return "frontend/chitietsanpham";
		} else {
			Page<Comment> danTocPage = commentServiceImpl.findAllDanhMuc(id, pageable);
			Pagination<Comment> page = new Pagination<Comment>(danTocPage, "/findsanphambyid/" + id);
			model.addAttribute("comment", page.getContent());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			model.addAttribute("status", status);
			model.addAttribute("sanPham", sanPhamServiceImpl.findByIdSanPham(id));
			model.addAttribute("noiDung", sanPhamServiceImpl.findByIdSanPham(id).getMota());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("listSanPham", anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham());
			List<Anhsanpham> asp = anhSanPhamServiceImpl.findByIdAnhSanPhamIdSanPham(id);
			model.addAttribute("listAnh", asp);
			model.addAttribute("title", sanPhamServiceImpl.findByIdSanPham(id).getTen());
			return "frontend/chitietsanpham";
		}
	}

	@GetMapping(value = { "/sanphamtheodanhmuc/{id}" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, @PathVariable("id") Integer id,
			HttpServletRequest request, @PageableDefault(size = 12) Pageable pageable) {
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
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoDanhMucActive(id, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/sanphamtheodanhmuc/" + id);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		} else {
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoDanhMucActive(id, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/sanphamtheodanhmuc/" + id);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		}
	}

	@GetMapping(value = { "/sanphamtheonhacungcap/{id}" })
	public String findAllTheoNhaCungCap(Model model, @ModelAttribute("status") String status,
			@PathVariable("id") Integer id, HttpServletRequest request, @PageableDefault(size = 12) Pageable pageable) {
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
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoNhaCungCapActive(id, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/sanphamtheodanhmuc/" + id);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		} else {
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoNhaCungCapActive(id, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/sanphamtheodanhmuc/" + id);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		}
	}

	@GetMapping(value = { "/timKiemSanPhamTheoGia" })
	public String sanPhamTheoGia(Model model, @ModelAttribute("status") String status,
			@RequestParam(name = "giabatdau") int giabatdau, @RequestParam(name = "giaketthuc") int giaketthuc,
			@PageableDefault(size = 12) Pageable pageable, HttpServletRequest request) {
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
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByGia(giabatdau, giaketthuc, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
					"/timKiemSanPhamTheoGia/" + "?giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		} else {
			model.addAttribute("title", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByGia(giabatdau, giaketthuc, pageable);
			Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
					"/timKiemSanPhamTheoGia/" + "?giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
			model.addAttribute("listSanPham", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
			model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachsanpham";
		}
	}

	@GetMapping(value = { "/timKiemSanPham" })
	public String timKiemSanPham(Model model, @ModelAttribute("status") String status,
			@RequestParam(name = "idDanhMuc", required = false) Integer idDanhMuc,
			@RequestParam(name = "idNhaCungCap", required = false) Integer idNhaCungCap,
			@RequestParam(name = "giabatdau", required = false) Integer giabatdau,
			@RequestParam(name = "giaketthuc", required = false) Integer giaketthuc,
			@PageableDefault(size = 12) Pageable pageable, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (idDanhMuc != null && idNhaCungCap != null && giabatdau != null && giaketthuc != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucNhaCungCapGiaSanPham(idDanhMuc,
						idNhaCungCap, giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
						"/timKiemSanPham/" + "?idDanhMuc=" + idDanhMuc + "&idNhaCungCap=" + idNhaCungCap + "&giabatdau="
								+ giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucNhaCungCapGiaSanPham(idDanhMuc,
						idNhaCungCap, giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
						"/timKiemSanPham/" + "?idDanhMuc=" + idDanhMuc + "&idNhaCungCap=" + idNhaCungCap + "&giabatdau="
								+ giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}

		} else if (idDanhMuc != null && idNhaCungCap != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucNhaCungCap(idDanhMuc,
						idNhaCungCap, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucNhaCungCap(idDanhMuc,
						idNhaCungCap, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		} else if (idDanhMuc != null && giabatdau != null && giaketthuc != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucGiaSanPham(idDanhMuc, giabatdau,
						giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoDanhMucGiaSanPham(idDanhMuc, giabatdau,
						giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		} else if (idNhaCungCap != null && giabatdau != null && giaketthuc != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoNhaCungCapGiaSanPham(idNhaCungCap,
						giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.timKiemTheoNhaCungCapGiaSanPham(idNhaCungCap,
						giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		} else if (giabatdau != null && giaketthuc != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByGia(giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByGia(giabatdau, giaketthuc, pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + "&giabatdau=" + giabatdau + "&giaketthuc=" + giaketthuc);
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		} else if (idDanhMuc != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoDanhMucActive(idDanhMuc,
						pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoDanhMucActive(idDanhMuc,
						pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ idDanhMuc + "&idNhaCungCap=" + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		} else if (idNhaCungCap != null) {
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
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoNhaCungCapActive(idNhaCungCap,
						pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			} else {
				model.addAttribute("title", "Danh sách sản phẩm");
				model.addAttribute("status", status);
				Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoNhaCungCapActive(idNhaCungCap,
						pageable);
				Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/timKiemSanPham/" + "?idDanhMuc="
						+ "&idNhaCungCap=" + idNhaCungCap + "&giabatdau=" + "&giaketthuc=");
				model.addAttribute("listSanPham", page.getContent());
				model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
				model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
				model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
				model.addAttribute("page", page);
				int offset = (page.getNumber() - 1) * page.getSize();
				model.addAttribute("items", offset);
				return "frontend/danhsachsanpham";
			}
		}
		return status;
	}
}
