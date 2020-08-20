package com.dangducton.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.dangducton.entities.Danhmuc;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Nhacungcap;
import com.dangducton.entities.Nhapsanpham;
import com.dangducton.entities.Sanpham;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhaCungCapServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class SanPhamController {
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	private DanhMucServiceImpl danhMucServiceImpl;

	@Autowired
	private SanPhamServiceImpl sanPhamServiceImpl;

	@Autowired
	private AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@Autowired
	private NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@Autowired
	private NhaCungCapServiceImpl nhaCungCapServiceImpl;

	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";

	@GetMapping(value = { "/admin/themsanpham" })
	public String themSanPham(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
		model.addAttribute("nhaCungCap", nhaCungCapServiceImpl.findByIdDanhMucActive());
		model.addAttribute("themSanPham", "Thêm sản phẩm");
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

		return "admin/themsanpham";
	}

	@GetMapping(value = { "/admin/listsanpham" })
	public String findAllSanPham(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
		model.addAttribute("status", status);
		Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllAnhSanPham(pageable);
		Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage, "/admin/listsanpham");
		model.addAttribute("listSanPham", page.getContent());
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
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

		return "admin/danhsachsanpham";
	}

	@GetMapping(value = { "/admin/sanphamtheodanhmuc" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@RequestParam(name = "iddanhmuc") Integer id, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
		model.addAttribute("status", status);
		Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllSanPhamTheoDanhMuc(id, pageable);
		Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
				"/admin/sanphamtheodanhmuc" + "?iddanhmuc=" + id);
		model.addAttribute("listSanPham", page.getContent());
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
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

		return "admin/danhsachsanpham";
	}

	@GetMapping(value = { "/admin/timKiemSanPhamTheoTen" })
	public String findAllSanPhamTheoTen(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@RequestParam(name = "search") String search, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhSachSanPham", "Danh sách sản phẩm");
		model.addAttribute("status", status);
		Page<Anhsanpham> danTocPage = anhSanPhamServiceImpl.findAllByTenSanPham(search, pageable);
		Pagination<Anhsanpham> page = new Pagination<Anhsanpham>(danTocPage,
				"/admin/timKiemSanPhamTheoTen" + "?search=" + search);
		model.addAttribute("listSanPham", page.getContent());
		model.addAttribute("danhMuc", danhMucServiceImpl.findByIdDanhMucActive());
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

		return "admin/danhsachsanpham";
	}

	@GetMapping("/admin/xoaanhsanpham")
	public String findById(Integer id, Model model, RedirectAttributes ra) {
		anhSanPhamServiceImpl.delete(id);
		ra.addFlashAttribute("status", "Xóa thành công");
		return "redirect:/admin/listsanpham";
	}

	@GetMapping("/admin/findonesanpham")
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

		return "admin/updatesanpham";
	}

	@GetMapping(path = "/admin/updateNotActivateSanPham/{id}")
	public String updateNotActivate(@PathVariable("id") Integer id, Sanpham dt, RedirectAttributes ra) {
		dt.setGiaban(sanPhamServiceImpl.findByIdSanPham(id).getGiaban());
		dt.setGiamgia(sanPhamServiceImpl.findByIdSanPham(id).getGiamgia());
		dt.setGianhap(sanPhamServiceImpl.findByIdSanPham(id).getGianhap());
		dt.setIdDanhmuc(sanPhamServiceImpl.findByIdSanPham(id).getIdDanhmuc());
		dt.setMota(sanPhamServiceImpl.findByIdSanPham(id).getMota());
		dt.setMotangan(sanPhamServiceImpl.findByIdSanPham(id).getMotangan());
		dt.setNgaytao(sanPhamServiceImpl.findByIdSanPham(id).getNgaytao());
		dt.setTen(sanPhamServiceImpl.findByIdSanPham(id).getTen());
		dt.setStatus(0);
		sanPhamServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/listsanpham";
	}

	@GetMapping(path = "/admin/updateActivateSanPham/{id}")
	public String updateActivate(@PathVariable("id") Integer id, Sanpham dt, RedirectAttributes ra) {
		dt.setGiaban(sanPhamServiceImpl.findByIdSanPham(id).getGiaban());
		dt.setGiamgia(sanPhamServiceImpl.findByIdSanPham(id).getGiamgia());
		dt.setGianhap(sanPhamServiceImpl.findByIdSanPham(id).getGianhap());
		dt.setIdDanhmuc(sanPhamServiceImpl.findByIdSanPham(id).getIdDanhmuc());
		dt.setMota(sanPhamServiceImpl.findByIdSanPham(id).getMota());
		dt.setMotangan(sanPhamServiceImpl.findByIdSanPham(id).getMotangan());
		dt.setNgaytao(sanPhamServiceImpl.findByIdSanPham(id).getNgaytao());
		dt.setTen(sanPhamServiceImpl.findByIdSanPham(id).getTen());
		dt.setStatus(1);
		sanPhamServiceImpl.save(dt);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/listsanpham";
	}

	@PostMapping(value = "/admin/addSanPham")
	public String addPhongBan(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
			@RequestParam(name = "tensanpham") String tenSanPham, @RequestParam(name = "iddanhmuc") Integer idDanhMuc,
			@RequestParam(name = "idnhacungcap") Integer idNhaCungCap, @RequestParam(name = "gianhap") Double giaNhap,
			@RequestParam(name = "giaban") Double giaBan, @RequestParam(name = "giakhuyenmai") Double giaKhuyenMai,
			@RequestParam(name = "motangan") String moTaNgan, @RequestParam(name = "motachitiet") String moTaChiTiet,
			Model model, RedirectAttributes ra) throws java.text.ParseException, IOException {
		List<Sanpham> listPhongBan = sanPhamServiceImpl.findAllByTenSanPham(tenSanPham);
		if (listPhongBan.size() >= 1) {
			ra.addFlashAttribute("status", "Tên sản phẩm đã tồn tại");
			return "redirect:/admin/themsanpham";
		}
		if (giaKhuyenMai == null) {
			Date ngayTao = new Date();
			Sanpham sp = new Sanpham();
			Danhmuc dm = new Danhmuc();
			dm = danhMucServiceImpl.findByIdDanhMuc(idDanhMuc);
			Nhacungcap ncc = new Nhacungcap();
			ncc = nhaCungCapServiceImpl.findByIdDanhMuc(idNhaCungCap);
			sp.setIdNhacungcap(ncc);
			sp.setIdDanhmuc(dm);
			sp.setTen(tenSanPham);
			sp.setGiaban(giaBan);
			sp.setGiamgia((double) 0);
			sp.setGianhap(giaNhap);
			sp.setMota(moTaChiTiet);
			sp.setMotangan(moTaNgan);
			sp.setNgaytao(ngayTao);
			sp.setStatus(1);

			sanPhamServiceImpl.save(sp);

			for (MultipartFile uploadedFile : uploadingFiles) {
				BufferedOutputStream stream = null;
				try {
					String fileName = uploadedFile.getOriginalFilename();
					String filePath = Paths.get(uploadDirectory, fileName).toString();
					stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(uploadedFile.getBytes());
					Anhsanpham asp = new Anhsanpham();
					asp.setIdSanpham(sp);
					asp.setStatus(1);
					asp.setUrlAnh("/upload/" + uploadedFile.getOriginalFilename());
					anhSanPhamServiceImpl.save(asp);
					stream.close();
				} catch (IOException e) {
				} finally {
					stream.close();
				}
			}
			Nhapsanpham nsp = new Nhapsanpham();
			nsp.setIdSanpham(sp);
			nsp.setNgaytao(ngayTao);
			nsp.setSoluong(0);
			nsp.setGianhap(sp.getGianhap());
			nsp.setStatus(1);
			nhapSanPhamServiceImpl.save(nsp);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/listsanpham";

		} else {
			Date ngayTao = new Date();
			Sanpham sp = new Sanpham();
			Danhmuc dm = new Danhmuc();
			dm = danhMucServiceImpl.findByIdDanhMuc(idDanhMuc);
			Nhacungcap ncc = new Nhacungcap();
			ncc = nhaCungCapServiceImpl.findByIdDanhMuc(idNhaCungCap);
			sp.setIdNhacungcap(ncc);
			sp.setIdDanhmuc(dm);
			sp.setTen(tenSanPham);
			sp.setGiaban(giaBan);
			sp.setGiamgia(giaKhuyenMai);
			sp.setGianhap(giaNhap);
			sp.setMota(moTaChiTiet);
			sp.setMotangan(moTaNgan);
			sp.setNgaytao(ngayTao);
			sp.setStatus(1);
			sanPhamServiceImpl.save(sp);

			for (MultipartFile uploadedFile : uploadingFiles) {
				BufferedOutputStream stream = null;
				try {
					String fileName = uploadedFile.getOriginalFilename();
					String filePath = Paths.get(uploadDirectory, fileName).toString();
					stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(uploadedFile.getBytes());
					Anhsanpham asp = new Anhsanpham();
					asp.setIdSanpham(sp);
					asp.setStatus(1);
					asp.setUrlAnh("/upload/" + uploadedFile.getOriginalFilename());
					anhSanPhamServiceImpl.save(asp);
					stream.close();
				} catch (IOException e) {
				} finally {
					stream.close();
				}
			}
			Nhapsanpham nsp = new Nhapsanpham();
			nsp.setIdSanpham(sp);
			nsp.setNgaytao(ngayTao);
			nsp.setSoluong(0);
			nsp.setStatus(1);
			nhapSanPhamServiceImpl.save(nsp);
			ra.addFlashAttribute("status", "Thêm thành công");
			return "redirect:/admin/listsanpham";
		}
	}

	@PostMapping(value = "/admin/updateSanPham")
	public String updatePhongBan(@RequestParam(name = "tensanpham") String tenSanPham,
			@RequestParam(name = "iddanhmuc") Integer idDanhMuc, @RequestParam(name = "id") Integer id,
			@RequestParam(name = "ngaytao") String ngayTao, @RequestParam(name = "gianhap") String giaNhap,
			@RequestParam(name = "giaban") String giaBan, @RequestParam(name = "giakhuyenmai") String giaKhuyenMai,
			@RequestParam(name = "idnhacungcap") Integer idNhaCungCap, @RequestParam(name = "motangan") String moTaNgan,
			@RequestParam(name = "status") Integer status, @RequestParam(name = "motachitiet") String moTaChiTiet,
			Model model, RedirectAttributes ra) throws java.text.ParseException, IOException {

		Sanpham sp = new Sanpham();
		Danhmuc dm = new Danhmuc();
		dm = danhMucServiceImpl.findByIdDanhMuc(idDanhMuc);
		Nhacungcap ncc = new Nhacungcap();
		ncc = nhaCungCapServiceImpl.findByIdDanhMuc(idNhaCungCap);

		double a = Double.valueOf(giaBan);
		double b = Double.valueOf(giaNhap);
		double c = Double.valueOf(giaKhuyenMai);
		Date ngayThanhLapParse = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ngayThanhLapParse = sdf.parse(ngayTao);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sp.setId(id);
		sp.setIdDanhmuc(dm);
		sp.setTen(tenSanPham);
		sp.setGiaban(a);
		sp.setGiamgia(c);
		sp.setGianhap(b);
		sp.setIdNhacungcap(ncc);
		sp.setMota(moTaChiTiet);
		sp.setMotangan(moTaNgan);
		sp.setNgaytao(ngayThanhLapParse);
		sp.setStatus(status);
		sanPhamServiceImpl.save(sp);
		ra.addFlashAttribute("status", "Sửa thành công");
		return "redirect:/admin/listsanpham";
	}

	@PostMapping(value = "/admin/themAnhSanPham")
	public String themAnhSanPham(
			@RequestParam(name = "uploadingFiles", required = false) List<MultipartFile> uploadingFiles,
			@RequestParam(name = "id") Integer id, Model model, RedirectAttributes ra)
			throws java.text.ParseException, IOException {

		Sanpham sp = new Sanpham();
		sp = sanPhamServiceImpl.findByIdSanPham(id);
		for (MultipartFile uploadedFile : uploadingFiles) {
			BufferedOutputStream stream = null;
			try {
				String fileName = uploadedFile.getOriginalFilename();
				String filePath = Paths.get(uploadDirectory, fileName).toString();
				stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(uploadedFile.getBytes());
				Anhsanpham asp = new Anhsanpham();
				asp.setIdSanpham(sp);
				asp.setStatus(1);
				asp.setUrlAnh("/upload/" + uploadedFile.getOriginalFilename());
				anhSanPhamServiceImpl.save(asp);
				stream.close();
			} catch (IOException e) {
			} finally {
				stream.close();
			}
		}
		ra.addFlashAttribute("status", "Thêm thành công");
		return "redirect:/admin/listsanpham";
	}
}
