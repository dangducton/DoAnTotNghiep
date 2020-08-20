package com.dangducton.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.dto.SanPhamBanChayDTO;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class ThongKeSanPhamController {
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;

	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;

	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@GetMapping(value = { "/admin/sanphambanchay" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("donHang", "Sản phẩm bán chạy");
		model.addAttribute("status", status);
		Page<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChay(pageable);
		Pagination<Chitietdonhang> page = new Pagination<Chitietdonhang>(danTocPage, "/admin/sanphambanchay");
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);

		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());

		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPham(asp.getIdSanpham().getId());
			sanPhamBanChay.add(new SanPhamBanChayDTO(asp.getIdSanpham().getId(), danTocPage1.getUrlAnh(), tongSoLuong,
					danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("sanPhamBanChay", sanPhamBanChay);

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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/sanphambanchay";
	}

	@GetMapping("/admin/taodoanhthusanphamtheongay")
	public String taodoanhthusanphamtheongay(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo ngày");
		List<Integer> ngay = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			ngay.add(i);
		}
		List<Integer> thang = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			thang.add(i);
		}
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("ngay", ngay);
		model.addAttribute("nam", nam);
		model.addAttribute("thang", thang);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taodoanhthutheongay";
	}

	@GetMapping("/admin/taodoanhthusanphamtheothang")
	public String taodoanhthusanphamtheothang(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo ngày");

		List<Integer> thang = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			thang.add(i);
		}
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("nam", nam);
		model.addAttribute("thang", thang);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taodoanhthutheothang";
	}

	@GetMapping("/admin/taodoanhthusanphamtheonam")
	public String taodoanhthusanphamtheonam(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo năm");

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("nam", nam);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taodoanhthutheonam";
	}

	@GetMapping("/admin/taothongkesanphambanchaytheongay")
	public String baocaothongkelluongtheophongban(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo ngày");
		List<Integer> ngay = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			ngay.add(i);
		}
		List<Integer> thang = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			thang.add(i);
		}
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("ngay", ngay);
		model.addAttribute("nam", nam);
		model.addAttribute("thang", thang);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taothongketheongay";
	}

	@GetMapping("/admin/taothongkesanphambanchaytheothang")
	public String taothongkesanphambanchaytheothang(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo tháng");

		List<Integer> thang = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			thang.add(i);
		}
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("nam", nam);
		model.addAttribute("thang", thang);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive();
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taothongketheothang";
	}

	@GetMapping("/admin/taothongkesanphambanchaytheonam")
	public String taothongkesanphambanchaytheonam(Model model, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request, @ModelAttribute("statusSuccess") String statusSuccess) {
		model.addAttribute("phongBan", "Thống kê sản phẩm bán chạy theo năm");

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		List<Integer> nam = new ArrayList<Integer>();
		for (int i = 2010; i <= year; i++) {
			nam.add(i);
		}
		model.addAttribute("nam", nam);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
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
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/taothongketheothangnam";
	}

	@GetMapping("/admin/thongkedoanhthutheongay")
	public String thongkedoanhthutheongay(@RequestParam("nam") Integer nam, @RequestParam("thang") Integer thang,
			@RequestParam("ngay") Integer ngay, Model model, @ModelAttribute("status") String status,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
		model.addAttribute("donHang", "Doanh thu sản phẩm theo ngày");
		model.addAttribute("status", "Doanh thu sản phẩm ngày " + ngay + " tháng " + thang + " năm " + nam);

		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoNgay(nam, thang, ngay);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		model.addAttribute("tongTienNhap", tongTienNhap);
		model.addAttribute("tongTienBan", tongTienBan);
		model.addAttribute("tongDoanhThu", tongDoanhThu);
		model.addAttribute("soLuong", soLuong);
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
		model.addAttribute("ngay", ngay);
		model.addAttribute("thang", thang);
		model.addAttribute("nam", nam);
		model.addAttribute("abc", "1");
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/doanhthusanpham";
	}

	@GetMapping("/admin/thongkedoanhthutheothang")
	public String thongkedoanhthutheothang(@RequestParam("nam") Integer nam, @RequestParam("thang") Integer thang,
			Model model, @ModelAttribute("status") String status, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request) {
		model.addAttribute("donHang", "Doanh thu sản phẩm theo tháng");
		model.addAttribute("status", "Doanh thu sản phẩm tháng " + thang + " năm " + nam);

		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoThang(nam, thang);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		model.addAttribute("tongTienNhap", tongTienNhap);
		model.addAttribute("tongTienBan", tongTienBan);
		model.addAttribute("tongDoanhThu", tongDoanhThu);
		model.addAttribute("soLuong", soLuong);
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
		model.addAttribute("thang", thang);
		model.addAttribute("nam", nam);
		model.addAttribute("abc", "2");
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/doanhthusanpham";
	}

	@GetMapping("/admin/thongkedoanhthutheonam")
	public String thongkedoanhthutheonam(@RequestParam("nam") Integer nam, Model model,
			@ModelAttribute("status") String status, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request) {
		model.addAttribute("donHang", "Doanh thu sản phẩm theo năm");
		model.addAttribute("status", "Doanh thu sản phẩm ngày năm " + nam);

		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoNam(nam);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		model.addAttribute("tongTienNhap", tongTienNhap);
		model.addAttribute("tongTienBan", tongTienBan);
		model.addAttribute("tongDoanhThu", tongDoanhThu);
		model.addAttribute("soLuong", soLuong);
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
		model.addAttribute("nam", nam);
		model.addAttribute("abc", "3");
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/doanhthusanpham";
	}

	@GetMapping("/admin/thongkesanphambanchaytheongay")
	public String thongkesanphambanchaytheongay(@RequestParam("nam") Integer nam, @RequestParam("thang") Integer thang,
			@RequestParam("ngay") Integer ngay, Model model, @ModelAttribute("status") String status,
			HttpServletRequest request) {
		model.addAttribute("donHang", "Sản phẩm bán chạy theo ngày");
		model.addAttribute("status", "Sản phẩm bán chạy ngày " + ngay + " tháng " + thang + " năm " + nam);
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoNgay(nam, thang, ngay);

		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());

		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoNgay(asp.getIdSanpham().getId(), nam,
					thang, ngay);
			sanPhamBanChay.add(new SanPhamBanChayDTO(asp.getIdSanpham().getId(), danTocPage1.getUrlAnh(), tongSoLuong,
					danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("sanPhamBanChay", sanPhamBanChay);
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
		model.addAttribute("abc", "1");
		model.addAttribute("ngay", ngay);
		model.addAttribute("thang", thang);
		model.addAttribute("nam", nam);
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/sanphambanchay";
	}

	@GetMapping("/admin/thongkesanphambanchaytheothang")
	public String thongkesanphambanchaytheothang(@RequestParam("nam") Integer nam, @RequestParam("thang") Integer thang,
			Model model, @ModelAttribute("status") String status, HttpServletRequest request) {
		model.addAttribute("donHang", "Sản phẩm bán chạy theo tháng");
		model.addAttribute("status", "Sản phẩm bán chạy tháng " + thang + " năm " + nam);
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoThang(nam, thang);

		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());

		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoThang(asp.getIdSanpham().getId(), nam,
					thang);
			sanPhamBanChay.add(new SanPhamBanChayDTO(asp.getIdSanpham().getId(), danTocPage1.getUrlAnh(), tongSoLuong,
					danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("sanPhamBanChay", sanPhamBanChay);
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
		model.addAttribute("abc", "2");
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		model.addAttribute("thang", thang);
		model.addAttribute("nam", nam);
		return "admin/sanphambanchay";
	}

	@GetMapping("/admin/thongkesanphambanchaytheonam")
	public String thongkesanphambanchaytheonam(@RequestParam("nam") Integer nam, Model model,
			@ModelAttribute("status") String status, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest request) {
		model.addAttribute("donHang", "Sản phẩm bán chạy theo năm");
		model.addAttribute("status", "Sản phẩm bán chạy năm " + nam);
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoNam(nam);

		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());

		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoNam(asp.getIdSanpham().getId(), nam);
			sanPhamBanChay.add(new SanPhamBanChayDTO(asp.getIdSanpham().getId(), danTocPage1.getUrlAnh(), tongSoLuong,
					danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("sanPhamBanChay", sanPhamBanChay);
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
		model.addAttribute("abc", "3");
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("nam", nam);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/sanphambanchay";
	}

	@GetMapping("/quantri/exportsanphambanchaytheongay/{ngay}/{thang}/{nam}")
	public ResponseEntity<StreamingResponseBody> findonehoadon(@PathVariable int ngay, @PathVariable int thang,
			@PathVariable int nam) throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoNgay(nam, thang, ngay);
		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoNgay(asp.getIdSanpham().getId(), nam,
					thang, ngay);
			sanPhamBanChay.add(new SanPhamBanChayDTO(danTocPage1.getIdSanpham().getId(), danTocPage1.getUrlAnh(),
					tongSoLuong, danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ SẢN PHẨM BÁN CHẠY");
		run2.addBreak();
		run2.setText("Ngày " + ngay + " tháng " + thang + " năm " + nam);

		XWPFTable tab = document.createTable();
		XWPFTableRow row = tab.getRow(0); // First row

		CTTblWidth width = tab.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));
		row.getCell(0).setText("Mã hàng");
		row.addNewTableCell().setText("ĐVT");
		row.addNewTableCell().setText("Tên sản phẩm");
		row.addNewTableCell().setText("Số lượng bán");
		row.addNewTableCell().setText("Giá nhập");
		row.addNewTableCell().setText("Giá bán");
		Locale localeEN = new Locale("vi", "VN");

		NumberFormat en = NumberFormat.getInstance(localeEN);
		for (SanPhamBanChayDTO qtct : sanPhamBanChay) {
			row = tab.createRow();
			String str1 = en.format(qtct.getGiaBan());
			String str2 = en.format(qtct.getGiaNhap());
			row.getCell(0).setText(qtct.getIdChiTietSanPham());
			row.getCell(1).setText("Chiếc");
			row.getCell(2).setText(qtct.getTenSanPham());
			row.getCell(3).setText("" + qtct.getSoLuong());
			row.getCell(4).setText(str2 + " đ");
			row.getCell(5).setText(str1 + " đ");
		}
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkesanphambanchaytheongay.docx\"")
				.body(document::write);
	}

	@GetMapping("/quantri/exportsanphambanchaytheothang/{thang}/{nam}")
	public ResponseEntity<StreamingResponseBody> exportsanphambanchaytheothang(@PathVariable int thang,
			@PathVariable int nam) throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoThang(nam, thang);
		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoThang(asp.getIdSanpham().getId(), nam,
					thang);
			sanPhamBanChay.add(new SanPhamBanChayDTO(danTocPage1.getIdSanpham().getId(), danTocPage1.getUrlAnh(),
					tongSoLuong, danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ SẢN PHẨM BÁN CHẠY");
		run2.addBreak();
		run2.setText("Tháng " + thang + " năm " + nam);

		XWPFTable tab = document.createTable();
		XWPFTableRow row = tab.getRow(0); // First row

		CTTblWidth width = tab.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));
		row.getCell(0).setText("Mã hàng");
		row.addNewTableCell().setText("ĐVT");
		row.addNewTableCell().setText("Tên sản phẩm");
		row.addNewTableCell().setText("Số lượng bán");
		row.addNewTableCell().setText("Giá nhập");
		row.addNewTableCell().setText("Giá bán");
		Locale localeEN = new Locale("vi", "VN");


		NumberFormat en = NumberFormat.getInstance(localeEN);
		for (SanPhamBanChayDTO qtct : sanPhamBanChay) {
			row = tab.createRow();
			String str1 = en.format(qtct.getGiaBan());
			String str2 = en.format(qtct.getGiaNhap());
			row.getCell(0).setText(qtct.getIdChiTietSanPham());
			row.getCell(1).setText("Chiếc");
			row.getCell(2).setText(qtct.getTenSanPham());
			row.getCell(3).setText("" + qtct.getSoLuong());
			row.getCell(4).setText(str2 + " đ");
			row.getCell(5).setText(str1 + " đ");
		}
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkesanphambanchaytheothang.docx\"")
				.body(document::write);
	}

	@GetMapping("/quantri/exportsanphambanchaytheonam/{nam}")
	public ResponseEntity<StreamingResponseBody> exportsanphambanchaytheonam(@PathVariable int nam)
			throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> danTocPage = chiTietDonHangServiceImpl.findAllSanPhamBanChayTheoNam(nam);
		List<SanPhamBanChayDTO> sanPhamBanChay = new ArrayList<SanPhamBanChayDTO>();
		for (Chitietdonhang asp : danTocPage) {
			Anhsanpham danTocPage1 = anhSanPhamServiceImpl.findSanPhamByChiTietDonHang(asp.getIdSanpham().getId());
			int tongSoLuong = chiTietDonHangServiceImpl.tinhSoLuongSanPhamTheoNam(asp.getIdSanpham().getId(), nam);
			sanPhamBanChay.add(new SanPhamBanChayDTO(danTocPage1.getIdSanpham().getId(), danTocPage1.getUrlAnh(),
					tongSoLuong, danTocPage1.getIdSanpham().getTen(), asp.getGianhapsanpham(), asp.getGiasanpham(),
					asp.getIdSanpham().getIdChitiet()));
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ SẢN PHẨM BÁN CHẠY");
		run2.addBreak();
		run2.setText("Năm " + nam);

		XWPFTable tab = document.createTable();
		XWPFTableRow row = tab.getRow(0); // First row

		CTTblWidth width = tab.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));
		row.getCell(0).setText("Mã hàng");
		row.addNewTableCell().setText("ĐVT");
		row.addNewTableCell().setText("Tên sản phẩm");
		row.addNewTableCell().setText("Số lượng bán");
		row.addNewTableCell().setText("Giá nhập");
		row.addNewTableCell().setText("Giá bán");
		Locale localeEN = new Locale("vi", "VN");

		NumberFormat en = NumberFormat.getInstance(localeEN);
		for (SanPhamBanChayDTO qtct : sanPhamBanChay) {
			row = tab.createRow();
			String str1 = en.format(qtct.getGiaBan());
			String str2 = en.format(qtct.getGiaNhap());
			row.getCell(0).setText(qtct.getIdChiTietSanPham());
			row.getCell(1).setText("Chiếc");
			row.getCell(2).setText(qtct.getTenSanPham());
			row.getCell(3).setText("" + qtct.getSoLuong());
			row.getCell(4).setText(str2 + " đ");
			row.getCell(5).setText(str1 + " đ");
		}
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkesanphambanchaytheonam.docx\"")
				.body(document::write);
	}
	
	@GetMapping("/quantri/exportdoanhthutheongay/{ngay}/{thang}/{nam}")
	public ResponseEntity<StreamingResponseBody> exportdoanhthutheongay(@PathVariable int ngay, @PathVariable int thang,
			@PathVariable int nam) throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoNgay(nam, thang, ngay);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ DOANH THU");
		run2.addBreak();
		run2.setText("Ngày " + ngay + " tháng " + thang + " năm " + nam);

		Locale localeEN = new Locale("vi", "VN");
		NumberFormat en = NumberFormat.getInstance(localeEN);
		String str1 = en.format(tongTienNhap);
		String str2 = en.format(tongTienBan);
		String str3 = en.format(tongDoanhThu);
		
		XWPFParagraph paragraph8 = document.createParagraph();
		paragraph8.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run8 = paragraph8.createRun();
		run8.setFontSize(13);
		run8.setFontFamily("Times New Roman");
		run8.setText("1. Tổng tiền nhập: " + str1+" đ");
		run8.addBreak();
		run8.setText("2. Tổng tiền bán: " +str2+" đ");
		run8.addBreak();
		run8.setText("3. Số lượng bán:" +soLuong);
		run8.addBreak();
		run8.setText("4. Tổng doanh thu: " +str3+" đ");
		run8.addBreak();
	
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkedoanhthutheongay.docx\"")
				.body(document::write);
	}

	@GetMapping("/quantri/exportdoanhthutheothang/{thang}/{nam}")
	public ResponseEntity<StreamingResponseBody> exportdoanhthutheothang(@PathVariable int thang,
			@PathVariable int nam) throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoThang(nam, thang);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ DOANH THU");
		run2.addBreak();
		run2.setText("Tháng " + thang + " năm " + nam);

		Locale localeEN = new Locale("vi", "VN");
		NumberFormat en = NumberFormat.getInstance(localeEN);
		String str1 = en.format(tongTienNhap);
		String str2 = en.format(tongTienBan);
		String str3 = en.format(tongDoanhThu);
		
		XWPFParagraph paragraph8 = document.createParagraph();
		paragraph8.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run8 = paragraph8.createRun();
		run8.setFontSize(13);
		run8.setFontFamily("Times New Roman");
		run8.setText("1. Tổng tiền nhập: " + str1+" đ");
		run8.addBreak();
		run8.setText("2. Tổng tiền bán: " +str2+" đ");
		run8.addBreak();
		run8.setText("3. Số lượng bán:" +soLuong);
		run8.addBreak();
		run8.setText("4. Tổng doanh thu: " +str3+" đ");
		run8.addBreak();
	
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkedoanhthutheothang.docx\"")
				.body(document::write);
	}

	@GetMapping("/quantri/exportdoanhthutheonam/{nam}")
	public ResponseEntity<StreamingResponseBody> exportdoanhthutheonam(@PathVariable int nam)
			throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.tinhDoanhThuTheoNam(nam);
		double tongTienNhap = 0;
		double tongTienBan = 0;
		double tongDoanhThu = 0;
		int soLuong = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tongTienNhap = tongTienNhap + chitietdonhang.getGianhapsanpham();
			tongTienBan = tongTienBan + chitietdonhang.getGiasanpham();
			tongDoanhThu = tongDoanhThu + (chitietdonhang.getGiasanpham() - chitietdonhang.getGianhapsanpham());
			soLuong = soLuong + chitietdonhang.getSoluong();
		}

		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setFontSize(13);
		run1.setFontFamily("Times New Roman");
		run1.setText("CÔNG TY TNHH Thủy Linh");
		run1.addBreak();
		run1.setText("Tòa nhà Thủy Linh, Số 33 Thái Hà,");
		run1.addBreak();
		run1.setText("Trung Liệt, Đống Đa, Hà Nội");
		run1.addBreak();
		run1.setText("Điện thoại: 024.3537.1526");
		run1.addBreak();
		run1.setText("EMAIL: info@thuylinh.vn");
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setFontSize(24);
		run2.isBold();
		run2.setFontFamily("Times New Roman");
		run2.setText("PHIẾU THỐNG KÊ DOANH THU");
		run2.addBreak();
		run2.setText("Năm " + nam);

		Locale localeEN = new Locale("vi", "VN");
		NumberFormat en = NumberFormat.getInstance(localeEN);
		String str1 = en.format(tongTienNhap);
		String str2 = en.format(tongTienBan);
		String str3 = en.format(tongDoanhThu);
		
		XWPFParagraph paragraph8 = document.createParagraph();
		paragraph8.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run8 = paragraph8.createRun();
		run8.setFontSize(13);
		run8.setFontFamily("Times New Roman");
		run8.setText("1. Tổng tiền nhập: " + str1 +" đ");
		run8.addBreak();
		run8.setText("2. Tổng tiền bán: " +str2+" đ");
		run8.addBreak();
		run8.setText("3. Số lượng bán:" +soLuong);
		run8.addBreak();
		run8.setText("4. Tổng doanh thu: " +str3+" đ");
		run8.addBreak();
	
		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội,....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người tạo phiếu");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongkedoanhthutheonam.docx\"")
				.body(document::write);
	}
}
