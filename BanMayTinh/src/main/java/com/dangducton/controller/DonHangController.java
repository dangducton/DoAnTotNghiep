package com.dangducton.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.dto.GioHangDTO;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.ThanhToanServiceImpl;
import com.dangducton.service.VanChuyenServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class DonHangController {
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;

	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;

	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@Autowired
	private DonHangServiceImpl donHangServiceImpl;

	@Autowired
	private ThanhToanServiceImpl thanhToanServiceImpl;

	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;

	@Autowired
	private VanChuyenServiceImpl vanChuyenServiceImpl;

	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@GetMapping(value = { "/admin/donhang" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("donHang", "Danh sách đơn hàng");
		model.addAttribute("status", status);
		Page<Donhang> danTocPage = donHangServiceImpl.findAllDanhMuc(pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/donhang");
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
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

		return "admin/donhang";
	}

	@GetMapping(value = { "/admin/timKiemDonHangByTen"})
	public String findonHangByTen(@RequestParam(name = "search") String search,Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("donHang", "Danh sách đơn hàng");
		model.addAttribute("status", status);
		Page<Donhang> danTocPage = donHangServiceImpl.findAllByTen(search, pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/timKiemDonHangByTen"+ "?search=" + search);
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
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

		return "admin/donhang";
	}
	
	@GetMapping(value = { "/admin/trangthaidonhang/{id}" })
	public String findDonHangTrangThaiDonHang(Model model, @PathVariable("id") int id,
			@ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("donHang", "Danh sách đơn hàng");
		model.addAttribute("status", status);
		Page<Donhang> danTocPage = donHangServiceImpl.findDonHangByTrangThaiDonHang(id, pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/trangthaidonhang/" + id);
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
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

		return "admin/donhang";
	}

	@GetMapping(value = { "/admin/findonedonhangbynguoidung/{id}" })
	public String findAllDonHangByNguoiDung(@PathVariable int id, Model model, @ModelAttribute("status") String status,
			HttpServletRequest request, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("donHang", "Danh sách đơn hàng");
		model.addAttribute("status", status);
		Page<Donhang> danTocPage = donHangServiceImpl.findByIdDonHangByNguoiDung(id, pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/findonedonhangbynguoidung/" + id);
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("thanhToan", thanhToanServiceImpl.findByIdDanhMucActive());
		model.addAttribute("vanChuyen", vanChuyenServiceImpl.findByIdDanhMucActive());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
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

		return "admin/donhang";
	}

	@PostMapping("/admin/updateDonHang")
	public String updatedonhang(Model model, @RequestParam(name = "id") Integer id, HttpServletRequest request,
			@RequestParam(name = "idtrangthaithanhtoan") Boolean idtrangthaithanhtoan,
			@RequestParam(name = "trangthaidonhang") Integer trangthaidonhang) {
		Donhang dh = donHangServiceImpl.findById(id).get();
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllGioHangTheoDonHang(id);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("title", "Trang chủ");
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Donhang> dh1 = donHangServiceImpl.findByListDonHangMoi();
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
		model.addAttribute("soLuongDonHang1", dh1.size());
		model.addAttribute("listDonHang1", dh1);
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());

		if (trangthaidonhang == 2) {
			dh.setTrangthaithanhtoan(true);
			dh.setTrangthaidonhang(trangthaidonhang);
			donHangServiceImpl.save(dh);
			return "redirect:/admin/donhang";
		}
		if (trangthaidonhang == 3) {
			for (Chitietdonhang chitietdonhang : list) {
				Chitietdonhang ctdh = chiTietDonHangServiceImpl.findById(chitietdonhang.getId()).get();
				ctdh.setStatus(0);
				chiTietDonHangServiceImpl.save(ctdh);
			}
			dh.setTrangthaithanhtoan(idtrangthaithanhtoan);
			dh.setTrangthaidonhang(trangthaidonhang);
			donHangServiceImpl.save(dh);
			return "redirect:/admin/donhang";
		} else {
			dh.setTrangthaithanhtoan(idtrangthaithanhtoan);
			dh.setTrangthaidonhang(trangthaidonhang);
			donHangServiceImpl.save(dh);
			return "redirect:/admin/donhang";
		}
	}

	@GetMapping("/admin/findonedonhang")
	public String findonedonhang(Integer id, Model model, HttpServletRequest request) {
		Donhang dh = donHangServiceImpl.findById(id).get();
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllGioHangTheoDonHang(id);
		List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		dh.setIdNguoiduyet(ng);
		donHangServiceImpl.save(dh);
		double tongSoTien = 0;
		for (Chitietdonhang ct : list) {
			tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
					ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("thongTinDonHang", "Thông tin đơn hàng");
		model.addAttribute("thanhToan", thanhToanServiceImpl.findByIdDanhMucActive());
		model.addAttribute("vanChuyen", vanChuyenServiceImpl.findByIdDanhMucActive());
		model.addAttribute("donHang", dh);
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("listGioHang", listGioHang);
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("title", "Trang chủ");
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Donhang> dh1 = donHangServiceImpl.findByListDonHangMoi();
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
		model.addAttribute("soLuongDonHang1", dh1.size());
		model.addAttribute("listDonHang1", dh1);
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());

		return "admin/updatedonhang";
	}

	@GetMapping("/admin/findonehoadon/{id}")
	public ResponseEntity<StreamingResponseBody> findonehoadon(@PathVariable int id)
			throws IOException, XmlException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		Donhang dh = donHangServiceImpl.findByIdDanhMuc(id);
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
		run2.setText("HÓA ĐƠN BÁN HÀNG");
		run2.addBreak();

		XWPFParagraph paragraph3 = document.createParagraph();
		paragraph3.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run3 = paragraph3.createRun();
		run3.setFontSize(13);
		run3.isBold();
		run3.setFontFamily("Times New Roman");
		LocalDate currentdate = LocalDate.now();
		int currentDay = currentdate.getDayOfMonth();
		int currentMonth = currentdate.getMonthValue();
		int currentYear = currentdate.getYear();
		run3.setText("Ngày " + currentDay + " tháng " + currentMonth + " năm " + currentYear);
		XWPFParagraph paragraph5 = document.createParagraph();
		paragraph5.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run5 = paragraph5.createRun();
		run5.setFontSize(13);
		run5.isBold();
		run5.setFontFamily("Times New Roman");
		run5.setText("Khách hàng: " + dh.getTennguoinhan());
		run5.addBreak();
		run5.setText("Địa chỉ: " + dh.getDiachi());
		run5.addBreak();
		run5.setText("Điện thoại: " + dh.getDienthoai());
		XWPFTable tab = document.createTable();
		XWPFTableRow row = tab.getRow(0); // First row

		CTTblWidth width = tab.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));
		row.getCell(0).setText("Mã hàng");
		row.addNewTableCell().setText("Tên hàng hóa");
		row.addNewTableCell().setText("ĐVT");
		row.addNewTableCell().setText("Số lượng");
		row.addNewTableCell().setText("Đơn giá");
		row.addNewTableCell().setText("Thành tiền");
		Locale localeEN = new Locale("vi", "VN");
		NumberFormat en = NumberFormat.getInstance(localeEN);
		for (GioHangDTO qtct : listGioHang) {
			row = tab.createRow();
			String str1 = en.format(qtct.getGiasanpham());
			String str2 = en.format(qtct.getGiasanpham() * qtct.getSoluong());

			row.getCell(0).setText(qtct.getIdChiTietSanPham());
			row.getCell(1).setText(qtct.getTen());
			row.getCell(2).setText("Chiếc");
			row.getCell(3).setText("" + qtct.getSoluong());
			row.getCell(4).setText(str1 + " đ");
			row.getCell(5).setText(str2 + " đ");
		}
		String str1 = en.format(tongSoTien);
		row = tab.createRow();
		row.getCell(4).setText("Thành tiền");
		row.getCell(5).setText(str1 + " đ");

		XWPFParagraph paragraph6 = document.createParagraph();
		paragraph6.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run6 = paragraph6.createRun();
		run6.setFontSize(13);
		run6.setFontFamily("Times New Roman");
		run6.setText("Thành phố Hà Nội, ....ngày....tháng....năm....");
		XWPFParagraph paragraph7 = document.createParagraph();
		paragraph7.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun run7 = paragraph7.createRun();
		run7.isBold();
		run7.setFontSize(13);
		run7.setFontFamily("Times New Roman");
		run7.setText("Người lập hóa đơn");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"thongtindonhang.docx\"")
				.body(document::write);
	}
}
