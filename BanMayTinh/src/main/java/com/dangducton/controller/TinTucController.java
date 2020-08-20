package com.dangducton.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.TonKhoDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Tintuc;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhaCungCapServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.service.TinTucServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class TinTucController {
	@Autowired
	private TinTucServiceImpl tinTucServiceImpl;

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

	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;

	@Autowired
	NhaCungCapServiceImpl nhaCungCapServiceImpl;

	@Autowired
	CommentServiceImpl commentServiceImpl;
	
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";

	@GetMapping(value = { "/admin/danhsachtintuc" })
	public String danhsachtintuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("tintuc", "Danh sách tin tức tin tức");
		model.addAttribute("status", status);
		Page<Tintuc> danTocPage = tinTucServiceImpl.findAllTinTuc(pageable);
		Pagination<Tintuc> page = new Pagination<Tintuc>(danTocPage, "/admin/danhsachtintuc");
		model.addAttribute("listTinTuc", page.getContent());
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

		return "admin/danhsachtintuc";
	}

	@GetMapping(value = { "/danhsachtintucfrontend" })
	public String danhsachtintucfrontend(Model model, @ModelAttribute("status") String status,
			@PageableDefault(size = 6) Pageable pageable) {
		model.addAttribute("module", "Tin tức");
		model.addAttribute("title", "Danh sách tin tức tin tức");
		Page<Tintuc> danTocPage = tinTucServiceImpl.findAllTinTucActive(pageable);
		Pagination<Tintuc> page = new Pagination<Tintuc>(danTocPage, "/danhsachtintucfrontend");
		model.addAttribute("listTinTuc", page.getContent());
		model.addAttribute("listTinTucMoiNhat", tinTucServiceImpl.findAllTinTucMoiNhat());
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
		model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
		model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		return "frontend/danhsachtintuc";
	}

	@GetMapping("/admin/updatetintuc")
	public String findById(Integer id, Model model,HttpServletRequest request) {
		Tintuc tt = tinTucServiceImpl.findById(id).get();
		model.addAttribute("tintuc", tt);
		model.addAttribute("suatintuc", "Sửa tin tức");
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

		return "admin/updatetintuc";
	}

	@GetMapping("/findtintucbyid/{id}")
	public String findtintucbyid(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("module", "Tin tức");
		Tintuc tt = tinTucServiceImpl.findById(id).get();
		model.addAttribute("tintucchitiet", tt);
		model.addAttribute("title", "Tin tức");
		model.addAttribute("listTinTucRanDom", tinTucServiceImpl.findAllTinTucRandom());
		model.addAttribute("listTinTucMoiNhat", tinTucServiceImpl.findAllTinTucMoiNhat());
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
		model.addAttribute("top3", anhSanPhamServiceImpl.findAllSanPhamSellNhieuNhap());
		model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
		return "frontend/chititettintuc";
	}

	@GetMapping(value = { "/admin/initthemtintuc" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Thêm tin tức");
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

		return "admin/themtintuc";
	}

	@GetMapping(path = "/admin/updateNotActivateTinTuc/{id}")
	public String updateNotActivate(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Tintuc tt = tinTucServiceImpl.findById(id).get();
		tt.setStatus(0);
		tinTucServiceImpl.save(tt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhsachtintuc";
	}

	@GetMapping(path = "/admin/updateActivateTinTuc/{id}")
	public String updateActivate(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Tintuc tt = tinTucServiceImpl.findById(id).get();
		tt.setStatus(1);
		tinTucServiceImpl.save(tt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhsachtintuc";
	}

	@GetMapping(value = { "/timKiemTinTucTheoTenTinTuc" })
	public String sanPhamTheoGia(Model model, @ModelAttribute("status") String status,
			@RequestParam(name = "ten") String ten, @PageableDefault(size = 6) Pageable pageable,
			HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			model.addAttribute("module", "Tin tức");
			model.addAttribute("tintuc", "Danh sách tin tức");
			model.addAttribute("status", status);
			Page<Tintuc> danTocPage = tinTucServiceImpl.findAllByTenSanPham(ten, pageable);
			Pagination<Tintuc> page = new Pagination<Tintuc>(danTocPage,
					"/timKiemTinTucTheoTenTinTuc/" + "?ten=" + ten);
			model.addAttribute("listTinTuc", page.getContent());
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
			model.addAttribute("listTinTucRanDom", tinTucServiceImpl.findAllTinTucRandom());
			model.addAttribute("listTinTucMoiNhat", tinTucServiceImpl.findAllTinTucMoiNhat());
			model.addAttribute("tongSoTien", tongSoTien);
			model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
			model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
			return "frontend/danhsachtintuc";
		} else {
			model.addAttribute("module", "Tin tức");
			model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
			model.addAttribute("status", status);
			model.addAttribute("listTinTucRanDom", tinTucServiceImpl.findAllTinTucRandom());
			model.addAttribute("listTinTucMoiNhat", tinTucServiceImpl.findAllTinTucMoiNhat());
			Page<Tintuc> danTocPage = tinTucServiceImpl.findAllByTenSanPham(ten, pageable);
			Pagination<Tintuc> page = new Pagination<Tintuc>(danTocPage,
					"/timKiemTinTucTheoTenTinTuc/" + "?ten=" + ten);
			model.addAttribute("listTinTuc", page.getContent());
			model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
			model.addAttribute("page", page);
			int offset = (page.getNumber() - 1) * page.getSize();
			model.addAttribute("items", offset);
			return "frontend/danhsachtintuc";
		}
	}

	@PostMapping(value = "/admin/themtintuc")
	public String addPhongBan(@RequestParam("uploadingFiles") MultipartFile file,
			@RequestParam(name = "tentintuc") String tentintuc, @RequestParam(name = "motangan") String motangan,
			@RequestParam(name = "motachitiet") String motachitiet, Model model, RedirectAttributes ra)
			throws IOException {
		Tintuc tt = new Tintuc();
		BufferedOutputStream stream = null;
		try {
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(file.getBytes());
			stream.close();
		} catch (IOException e) {
			stream.close();
		}
		Date date = new Date();
		tt.setMotangan(motangan);
		tt.setNgaytao(date);
		tt.setNoidung(motachitiet);
		tt.setStatus(1);
		tt.setTentintuc(tentintuc);
		tt.setHinhanh("/upload/" + file.getOriginalFilename());
		tinTucServiceImpl.save(tt);
		ra.addFlashAttribute("status", "Thêm thành công");
		return "redirect:/admin/danhsachtintuc";
	}

	@PostMapping(value = "/admin/updatetintuc")
	public String updatetintuc(@RequestParam("uploadingFiles") MultipartFile file,
			@RequestParam(name = "id") Integer id, @RequestParam(name = "tentintuc") String tentintuc,
			@RequestParam(name = "motangan") String motangan, @RequestParam(name = "motachitiet") String motachitiet,
			Model model, RedirectAttributes ra) throws IOException {
		if (!file.isEmpty()) {
			Tintuc tt = tinTucServiceImpl.findById(id).get();
			BufferedOutputStream stream = null;
			try {
				String fileName = file.getOriginalFilename();
				String filePath = Paths.get(uploadDirectory, fileName).toString();
				stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (IOException e) {
				stream.close();
			}
			tt.setMotangan(motangan);
			tt.setNoidung(motachitiet);
			tt.setStatus(1);
			tt.setTentintuc(tentintuc);
			tt.setHinhanh("/upload/" + file.getOriginalFilename());
			tinTucServiceImpl.save(tt);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/danhsachtintuc";
		} else {
			Tintuc tt = tinTucServiceImpl.findById(id).get();
			tt.setMotangan(motangan);
			tt.setNoidung(motachitiet);
			tt.setStatus(1);
			tt.setTentintuc(tentintuc);
			tinTucServiceImpl.save(tt);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/danhsachtintuc";
		}
	}
}
