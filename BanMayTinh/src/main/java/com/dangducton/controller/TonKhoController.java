package com.dangducton.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Nhapsanpham;
import com.dangducton.entities.Sanpham;
import com.dangducton.dto.GioHangDTO;
import com.dangducton.dto.NhapSanPhamDTO;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class TonKhoController {
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;

	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;

	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;

	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	
	@Autowired
	DonHangServiceImpl donHangServiceImpl;

	@GetMapping("/admin/nhapthemsanpham")
	public String xoaAnhSanPham(Integer id, Model model,HttpServletRequest request) {
		Sanpham sp = new Sanpham();
		sp = sanPhamServiceImpl.findByIdSanPham(id);
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
		model.addAttribute("tenSanPham", sp.getTen());
		model.addAttribute("sanPham", sp);
		List<Anhsanpham> lasp = anhSanPhamServiceImpl.findByIdAnhSanPhamIdSanPham(id);
		model.addAttribute("anhSanPham", lasp);
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

		return "admin/nhapthemsanpham";
	}

	@PostMapping("/admin/nhapsanpham")
	public String nhapThemSanPham(@RequestParam(name = "id") Integer id, @RequestParam(name = "soluong") int soluong,
			Model model, RedirectAttributes ra) {
		Sanpham sp = sanPhamServiceImpl.findById(id).get();
		Date date = new Date();
		Nhapsanpham nsp = new Nhapsanpham();
		nsp.setIdSanpham(sp);
		nsp.setNgaytao(date);
		nsp.setGianhap(sp.getGianhap());
		nsp.setSoluong(soluong);
		nsp.setStatus(1);
		nhapSanPhamServiceImpl.save(nsp);
		ra.addFlashAttribute("status", "Thêm thành công");
		return "redirect:/admin/nhapsanpham";
	}

	@GetMapping(value = { "/admin/tonkho" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Quản lý tồn kho");
		model.addAttribute("status", status);
		Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllAnhSanPham(pageable);
		Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/admin/tonkho");
		List<TonKhoDTO> listTonKho = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				int tonKho = tongNhap - 0;
				listTonKho.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, 0, tonKho,
						asp.getIdSanpham().getId()));
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				int tonKho = tongNhap - tongBan;
				listTonKho.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan, tonKho,
						asp.getIdSanpham().getId()));
			}
		}

		model.addAttribute("listTonKho", listTonKho);
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

		return "admin/tonkho";
	}

	@GetMapping("/admin/exportphieunhap/{id}")
	public ResponseEntity<StreamingResponseBody> findonehoadon(@PathVariable int id) throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		Nhapsanpham dh = nhapSanPhamServiceImpl.findById(id).get();
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllGioHangTheoDonHang(id);
		List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
		double tongSoTien = 0;
		for (Chitietdonhang ct : list) {
			tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
					ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),
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
		run2.setText("PHIẾU NHẬP KHO");

		XWPFParagraph paragraph3 = document.createParagraph();
		paragraph3.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run3 = paragraph3.createRun();
		run3.setFontSize(13);
		run3.isBold();
		run3.setFontFamily("Times New Roman");
		Date date = dh.getNgaytao();
		SimpleDateFormat formatNowDay = new SimpleDateFormat("dd");
		SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");

		String currentDay = formatNowDay.format(date);
		String currentMonth = formatNowMonth.format(date);
		String currentYear = formatNowYear.format(date);
		run3.setText("Ngày " + currentDay + " tháng " + currentMonth + " năm " + currentYear);
		Locale localeEN = new Locale("vi", "VN");
		NumberFormat en = NumberFormat.getInstance(localeEN);
		String str1 = en.format(dh.getGianhap());
		String str2 = en.format(dh.getGianhap() * dh.getSoluong());
		XWPFParagraph paragraph5 = document.createParagraph();
		paragraph5.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run5 = paragraph5.createRun();
		run5.setFontSize(13);
		run5.isBold();
		run5.setFontFamily("Times New Roman");
		run5.setText("Mã sản phẩm: " + dh.getIdSanpham().getIdChitiet());
		run5.addBreak();
		run5.setText("Tên sản phẩm: " + dh.getIdSanpham().getTen());
		run5.addBreak();
		run5.setText("Giá nhập: " + str1 + " đ");
		run5.addBreak();
		run5.setText("Số lượng: " + dh.getSoluong());
		run5.addBreak();
		run5.setText("Tổng tiền: " + str2);

		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.addBreak();
		run6.setText("Thành phố Hà Nội, ....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người lập hóa đơn");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"baocaonhapkho.docx\"")
				.body(document::write);
	}

	@GetMapping("/admin/nhapsanpham")
	public String danhSachSanpham(Model model, @PageableDefault(size = 12) Pageable pageable,
			@ModelAttribute("status") String status, HttpServletRequest request) {
		model.addAttribute("danhMuc", "Danh sách nhập");
		model.addAttribute("status", status);
		Page<Nhapsanpham> danTocPage = nhapSanPhamServiceImpl.findAllByNhapSanPham(pageable);
		Pagination<Nhapsanpham> page = new Pagination<Nhapsanpham>(danTocPage, "/admin/nhapsanpham");
		List<NhapSanPhamDTO> listTonKho = new ArrayList<NhapSanPhamDTO>();
		for (Nhapsanpham asp : danTocPage) {
			Anhsanpham asp1 = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(asp.getIdSanpham().getId());
			listTonKho.add(new NhapSanPhamDTO(asp.getId(), asp.getIdSanpham().getId(), asp.getIdSanpham().getTen(),
					asp.getIdSanpham().getIdChitiet(), asp.getGianhap(), asp.getSoluong(), asp.getNgaytao(),
					asp1.getUrlAnh()));
		}

		model.addAttribute("listTonKho", listTonKho);
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
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

		model.addAttribute("items", offset);
		return "admin/nhapsanpham";
	}

	@GetMapping(value = { "/admin/danhsachnhapsanpam" })
	public String findAllSanPham(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Quản lý tồn kho");
		model.addAttribute("status", status);
		Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllBySanPhamTheoNhap(pageable);
		Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/admin/danhsachnhapsanpam");

		model.addAttribute("listNhap", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
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

		model.addAttribute("items", offset);
		return "admin/tonkho";
	}
}
