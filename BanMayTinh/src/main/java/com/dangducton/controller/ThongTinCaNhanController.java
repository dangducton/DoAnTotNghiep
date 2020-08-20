package com.dangducton.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.dto.GioHangDTO;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.service.ThanhToanServiceImpl;
import com.dangducton.service.VanChuyenServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class ThongTinCaNhanController {

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
	DonHangServiceImpl donHangServiceImpl;
	@Autowired
	private ThanhToanServiceImpl thanhToanServiceImpl;
	@Autowired
	private VanChuyenServiceImpl vanChuyenServiceImpl;

	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";

	@GetMapping(value = { "/banhang/thongtincanhan" })
	public String findAllDonHangByNguoiDung(Model model, @ModelAttribute("status") String status,
			HttpServletRequest request, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("title", "Thông tin cá nhân");
		model.addAttribute("status", status);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		model.addAttribute("thongtinnguoidung", ng);
		Page<Donhang> danTocPage = donHangServiceImpl.findByIdDonHangByNguoiDung(ng.getId(), pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/thongtincanhan/" + ng.getId());
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
		double tongSoTien = 0;
		for (Chitietdonhang ct1 : list1) {
			tongSoTien = tongSoTien + (ct1.getGiasanpham() * ct1.getSoluong());
		}
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		return "frontend/thongtincanhan";
	}

	

	@GetMapping(value = { "/banhang/thaydoimatkhau" })
	public String doimatkhau(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("title", "Thông tin cá nhân");
		model.addAttribute("status", status);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		model.addAttribute("thongtinnguoidung", ng);
		Page<Donhang> danTocPage = donHangServiceImpl.findByIdDonHangByNguoiDung(ng.getId(), pageable);
		Pagination<Donhang> page = new Pagination<Donhang>(danTocPage, "/admin/thongtincanhan/" + ng.getId());
		model.addAttribute("listDonHang", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
		double tongSoTien = 0;
		for (Chitietdonhang ct1 : list1) {
			tongSoTien = tongSoTien + (ct1.getGiasanpham() * ct1.getSoluong());
		}
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		return "frontend/doimatkhau";
	}

	@GetMapping("/banhang/findonedonhang")
	public String findonedonhang(Integer id, Model model, HttpServletRequest request) {
		Donhang dh = donHangServiceImpl.findById(id).get();
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllGioHangTheoDonHang(id);
		List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		model.addAttribute("thongtinnguoidung", ng);
		double tongSoTien = 0;
		for (Chitietdonhang ct : list) {
			tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new GioHangDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
					ct.getSoluong(), ct.getId(), asp.getIdSanpham().getId(), ct.getIdDonhang().getId(),
					ct.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("title", "Thông tin đơn hàng");
		model.addAttribute("thanhToan", thanhToanServiceImpl.findByIdDanhMucActive());
		model.addAttribute("vanChuyen", vanChuyenServiceImpl.findByIdDanhMucActive());
		model.addAttribute("donHang", dh);
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("listGioHang", listGioHang);
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		return "frontend/chitietdonhang";
	}

	@PostMapping(value = { "/banhang/updateNguoiDung" })
	public String updateNhanVienHeThong(Model model, @RequestParam("uploadingFiles") MultipartFile file,
			@RequestParam("hoten") String hoten, @RequestParam("id") Integer id,
			@RequestParam("dienthoai") String dienthoai, @RequestParam("diachi") String diachi,
			@RequestParam("gioitinh") int gioitinh, RedirectAttributes ra) throws ParseException, IOException {
		if (!file.isEmpty()) {
			if (hoten.length() >= 5) {
				Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
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
				ng.setHoten(hoten);
				ng.setDienthoai(dienthoai);
				ng.setDiachi(diachi);
				ng.setGioitinh(gioitinh);
				ng.setAnh("/upload/" + file.getOriginalFilename());
				nguoiDungServiceImpl.update(ng);
				ra.addFlashAttribute("status", "Cập nhật thành công thành công");
				return "redirect:/banhang/thongtincanhan";
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập thêm họ tên");
				return "redirect:/banhang/thongtincanhan";
			}
		} else {
			if (hoten.length() >= 5) {
				Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
				ng.setHoten(hoten);
				ng.setDienthoai(dienthoai);
				ng.setDiachi(diachi);
				ng.setGioitinh(gioitinh);
				nguoiDungServiceImpl.update(ng);
				ra.addFlashAttribute("status", "Cập nhật thành công");
				return "redirect:/banhang/thongtincanhan";
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập thêm họ tên");
				return "redirect:/banhang/thongtincanhan";
			}
		}
	}

	@PostMapping(value = { "/banhang/doimatkhau" })
	public String doimaktkhau(Model model, @RequestParam("matkhaucu") String matkhaucu, HttpServletRequest request,
			@RequestParam("matkhaumoi") String matkhaumoi, @RequestParam("nhaplaimatkhaumoi") String nhaplaimatkhaumoi,
			RedirectAttributes ra) {
		if (matkhaumoi.length() >= 8) {
			if (nhaplaimatkhaumoi.equals(matkhaumoi)) {
				Principal principal = request.getUserPrincipal();
				Nguoidung ng = new Nguoidung();
				ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
				if (nguoiDungServiceImpl.checkIfValidOldPassword(ng.getPassword(), matkhaucu) == true) {
					nguoiDungServiceImpl.changeUserPassword(ng, matkhaumoi);
					ra.addFlashAttribute("status", "Cập nhật mật khẩu thành công");
					return "redirect:/banhang/thongtincanhan";
				} else {
					ra.addFlashAttribute("status", "Mật khẩu cũ không đúng");
					return "redirect:/banhang/thaydoimatkhau";
				}
			} else {
				ra.addFlashAttribute("status", "Nhập lại mật khẩu không đúng");
				return "redirect:/banhang/thaydoimatkhau";
			}
		} else {
			ra.addFlashAttribute("status", "Mật khẩu phải lớn hơn 8 ký tự");
			return "redirect:/banhang/thaydoimatkhau";
		}
	}

}
