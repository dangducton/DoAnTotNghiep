package com.dangducton.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Nhapsanpham;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;

@Controller
public class HomeController {
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
	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;
	@Autowired
	CommentServiceImpl commentServiceImpl;
	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;

	@GetMapping("/admin/home")
	public String main(Model model, HttpServletRequest request) {
		model.addAttribute("danhGia", commentServiceImpl.countcomment());
		model.addAttribute("donHang", donHangServiceImpl.countdonhang());
		model.addAttribute("sanPham", sanPhamServiceImpl.count());
		model.addAttribute("sanPhamYeuThich", sanPhamYeuThichServiceImpl.countsanphamyeuthich());
		model.addAttribute("nguoiDung", nguoiDungServiceImpl.countNguoiDung());
		model.addAttribute("donHangMoi", donHangServiceImpl.countdonhangmoi());
		model.addAttribute("quanTri", nguoiDungServiceImpl.countQuanTri());
		List<Nhapsanpham> nsp = nhapSanPhamServiceImpl.findAllByNhapSanPhamTinhTienNhap();
		List<Chitietdonhang> ctdh = chiTietDonHangServiceImpl.findByIdChiTietDonHangActive();
		double tiennhap = 0;
		double tienban = 0;
		double tienlai = 0;
		for (Chitietdonhang chitietdonhang : ctdh) {
			tienban = tienban + (chitietdonhang.getSoluong() * chitietdonhang.getGiasanpham());
		}
		for (Nhapsanpham nhapsanpham : nsp) {
			tiennhap = tiennhap + (nhapsanpham.getGianhap() * nhapsanpham.getSoluong());
		}
		tienlai = tienban - tiennhap;
		model.addAttribute("tiennhap", tiennhap);
		model.addAttribute("tienban", tienban);
		model.addAttribute("tienlai", tienlai);

		
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

		return "admin/home";
	}
}

