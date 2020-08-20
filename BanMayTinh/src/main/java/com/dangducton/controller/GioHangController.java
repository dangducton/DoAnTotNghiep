package com.dangducton.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.CommentDTO;
import com.dangducton.dto.GioHangDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Comment;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Sanpham;
import com.dangducton.entities.Trainingcommenttot;
import com.dangducton.entities.Trainingcommentxau;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.service.TrainingcommenttotServiceImpl;
import com.dangducton.service.TrainingcommentxauServiceImpl;

@Controller
public class GioHangController {
	@Autowired
	private TrainingcommenttotServiceImpl trainingcommenttotServiceImpl;
	@Autowired
	private TrainingcommentxauServiceImpl trainingcommentxauServiceImpl;
	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;
	@Autowired
	CommentServiceImpl commentServiceImpl;

	@GetMapping("/banhang/giohang")
	public String danhSachSanpham(Model model, @PageableDefault(size = 12) Pageable pageable,
			@ModelAttribute("status") String status, HttpServletRequest request) {
		model.addAttribute("module", "Shop");
		model.addAttribute("gioHang", "Giỏ hàng");
		model.addAttribute("status", status);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
		List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
		double tongSoTien = 0;
		for (Chitietdonhang ct : list) {
			tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
					ct.getSoluong(), ct.getIdSanpham().getId(), ct.getIdSanpham().getId(), asp.getIdSanpham().getId(),
					asp.getIdSanpham().getIdChitiet()));
		}

		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("listGioHang", listGioHang);
		return "frontend/giohang";
	}

	@PostMapping("/banhang/danhgiasanpham")
	public String guiDanhGiaSanPham(Model model, HttpServletRequest request,
			@RequestParam(name = "noidung") String noiDung, @RequestParam(name = "idSanPham") Integer idSanPham,
			RedirectAttributes ra) {
		List<String> danTocPage = new ArrayList<String>();
		danTocPage.add(noiDung);
		HashSet<String> posWord = new HashSet<String>();
		HashSet<String> negWord = new HashSet<String>();
		HashSet<String> dictionary = new HashSet<String>();
		List<String> dtWord = new ArrayList<String>();

		List<Trainingcommentxau> tncmtx = trainingcommentxauServiceImpl.findAll1();
		for (Trainingcommentxau cm : tncmtx) {
			negWord.add(cm.getTen().trim().toLowerCase());
			dictionary.add(cm.getTen().trim().toLowerCase());
		}

		List<Comment> cmt = commentServiceImpl.findByIdCMT();
		for (Comment cmt1 : cmt) {
			dtWord.add(cmt1.getNoidung().trim().toLowerCase());
		}

		List<Trainingcommenttot> tncmtt = trainingcommenttotServiceImpl.findAll1();
		for (Trainingcommenttot cm1 : tncmtt) {
			posWord.add(cm1.getTen().trim().toLowerCase());
			dictionary.add(cm1.getTen().trim().toLowerCase());
		}

		int totWordCount = posWord.size() + negWord.size();
		int negWordCount = negWord.size();
		int posWordCount = posWord.size();
		double negProb = 1.0 * negWordCount / totWordCount;
		double posProb = 1.0 * posWordCount / totWordCount;
		for (String cm12 : danTocPage) {
			double predict_neg_prob = negProb;
			double predict_pos_prob = posProb;
			String cm = cm12.toLowerCase();
			int found_neg = 0;
			int found_pos = 0;
			for (String keyword : dictionary) {
				if (cm.contains(keyword)) {
					System.out.println(cm);
					cm = cm.replace(keyword, "");
					if (negWord.contains(keyword)) {
						predict_neg_prob = predict_neg_prob * 1.0 / negWordCount;
						found_neg++;
					}
					if (posWord.contains(keyword)) {
						predict_pos_prob = predict_pos_prob * 1.0 / posWordCount;
						found_pos++;
					}
				}
			}
			if (found_neg == 0)
				predict_neg_prob = 0;
			if (found_pos == 0)
				predict_pos_prob = 0;
			if (found_neg > found_pos) {
				Principal principal = request.getUserPrincipal();
				Nguoidung ng = new Nguoidung();
				ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
				Date date = new Date();
				Comment cm1 = new Comment();
				cm1.setEmail(ng.getEmail());
				cm1.setIdNguoidung(ng);
				cm1.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
				cm1.setNgaytao(date);
				cm1.setNoidung(noiDung);
				cm1.setStatus(0);
				cm1.setTennguoidung(ng.getHoten());
				commentServiceImpl.save(cm1);
				ra.addFlashAttribute("status", "Cảm ơn bạn đã đánh giá sản phẩm. Bình luận của bạn đang chờ phê duyệt");
				return "redirect:/findsanphambyid/" + idSanPham;
			} else if (found_neg < found_pos) {
				Principal principal = request.getUserPrincipal();
				Nguoidung ng = new Nguoidung();
				ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
				Date date = new Date();
				Comment cm1 = new Comment();
				cm1.setEmail(ng.getEmail());
				cm1.setIdNguoidung(ng);
				cm1.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
				cm1.setNgaytao(date);
				cm1.setNoidung(noiDung);
				cm1.setStatus(1);
				cm1.setTennguoidung(ng.getHoten());
				commentServiceImpl.save(cm1);
				ra.addFlashAttribute("status", "Cảm ơn bạn đã đánh giá sản phẩm");
				return "redirect:/findsanphambyid/" + idSanPham;
			} else {
				Principal principal = request.getUserPrincipal();
				Nguoidung ng = new Nguoidung();
				ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
				Date date = new Date();
				Comment cm1 = new Comment();
				cm1.setEmail(ng.getEmail());
				cm1.setIdNguoidung(ng);
				cm1.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
				cm1.setNgaytao(date);
				cm1.setNoidung(noiDung);
				cm1.setStatus(1);
				cm1.setTennguoidung(ng.getHoten());
				commentServiceImpl.save(cm1);
				ra.addFlashAttribute("status", "Cảm ơn bạn đã đánh giá sản phẩm");
				return "redirect:/findsanphambyid/" + idSanPham;
			}
		}
		return noiDung;
	}

	@PostMapping("/banhang/updateSanPhamGioHang")
	public String capNhatGioHang(@RequestParam(name = "soluong") Integer soLuong,
			@RequestParam(name = "idSanPham") Integer idSanPham, RedirectAttributes ra, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		int idNguoiDung = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName()).getId();
		Chitietdonhang ctdh1 = chiTietDonHangServiceImpl.findAllByGioHang(idNguoiDung, idSanPham);
		Sanpham sp = sanPhamServiceImpl.findByIdSanPham(idSanPham);
		Integer tongNhap = nhapSanPhamServiceImpl.groupBy(idSanPham);
		System.out.println(tongNhap);
		Integer tongBan = chiTietDonHangServiceImpl.groupBy(idSanPham);
		System.out.println(tongBan);
		Integer conLai = tongNhap - tongBan;
		if (conLai > soLuong) {
			if (ctdh1 != null) {
				Chitietdonhang ctdh = new Chitietdonhang();
				ctdh.setId(ctdh1.getId());
				ctdh.setGiasanpham(ctdh1.getGiasanpham());
				ctdh.setGianhapsanpham(ctdh1.getGianhapsanpham());
				ctdh.setGiotao(ctdh1.getGiotao());
				ctdh.setIdNguoidung(ctdh1.getIdNguoidung());
				ctdh.setNgaytao(ctdh1.getNgaytao());
				ctdh.setSoluong(soLuong);
				ctdh.setIdSanpham(ctdh1.getIdSanpham());
				ctdh.setStatus(1);
				chiTietDonHangServiceImpl.save(ctdh);
				ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
				System.out.println("save");
				return "redirect:/banhang/giohang";
			} else {
				if (sp.getGiamgia() != 0) {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban() - (sp.getGiaban() - sp.getGiamgia()));
					ctdh.setGianhapsanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham).getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(soLuong);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					System.out.println("update");
					return "redirect:/banhang/giohang";
				} else {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban());
					ctdh.setGianhapsanpham(sp.getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(soLuong);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					System.out.println("nhunao");
					return "redirect:/banhang/giohang";
				}
			}
		} else {
			ra.addFlashAttribute("status", "Số lượng tồn kho không đủ");
			return "redirect:/banhang/giohang";
		}
	}

	@PostMapping("/banhang/deletegiohangbyidgiohang")
	public String deleteSanPhamGioHang(@RequestParam(name = "idGioHang") Integer id, RedirectAttributes ra,
			HttpServletRequest request) {
		chiTietDonHangServiceImpl.delete(id);
		ra.addFlashAttribute("status", "Xóa thành công");
		return "redirect:/banhang/giohang";

	}

	@PostMapping("/banhang/themmoivaogiohang")
	public String themMoiVaoGioHang(@RequestParam(name = "soluong") Integer soLuong,
			@RequestParam(name = "idSanPham") Integer idSanPham, RedirectAttributes ra, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		int idNguoiDung = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName()).getId();
		Chitietdonhang ctdh1 = chiTietDonHangServiceImpl.findAllByGioHang(idNguoiDung, idSanPham);
		Sanpham sp = sanPhamServiceImpl.findByIdSanPham(idSanPham);
		Integer tongNhap = nhapSanPhamServiceImpl.groupBy(idSanPham);

		Integer tongBan = null;
		tongBan = chiTietDonHangServiceImpl.groupBy(idSanPham);

		if (tongBan == null && tongNhap != null) {
			if (ctdh1 != null) {
				ctdh1.setGiasanpham(ctdh1.getGiasanpham());
				ctdh1.setGianhapsanpham(ctdh1.getGianhapsanpham());
				ctdh1.setGiotao(ctdh1.getGiotao());
				ctdh1.setIdNguoidung(ctdh1.getIdNguoidung());
				ctdh1.setNgaytao(ctdh1.getNgaytao());
				ctdh1.setSoluong(soLuong);
				ctdh1.setIdSanpham(ctdh1.getIdSanpham());
				ctdh1.setStatus(1);
				chiTietDonHangServiceImpl.save(ctdh1);
				ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
				return "redirect:/banhang/giohang";
			} else {
				if (sp.getGiamgia() != 0) {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban() - (sp.getGiaban() - sp.getGiamgia()));
					ctdh.setGianhapsanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham).getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(soLuong);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				} else {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban());
					ctdh.setGianhapsanpham(sp.getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(soLuong);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				}
			}
		} else {
			Integer conLai = tongNhap - tongBan;
			if (conLai < soLuong) {
				ra.addFlashAttribute("status", "Số lượng tồn kho không đủ");
				return "redirect:/banhang/giohang";
			} else {
				if (ctdh1 != null) {
					ctdh1.setGiasanpham(ctdh1.getGiasanpham());
					ctdh1.setGianhapsanpham(ctdh1.getGianhapsanpham());
					ctdh1.setGiotao(ctdh1.getGiotao());
					ctdh1.setIdNguoidung(ctdh1.getIdNguoidung());
					ctdh1.setNgaytao(ctdh1.getNgaytao());
					ctdh1.setSoluong(soLuong);
					ctdh1.setIdSanpham(ctdh1.getIdSanpham());
					ctdh1.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh1);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				} else {
					if (sp.getGiamgia() != 0) {
						Date date = new Date();
						Chitietdonhang ctdh = new Chitietdonhang();
						ctdh.setGiasanpham(sp.getGiaban() - (sp.getGiaban() - sp.getGiamgia()));
						ctdh.setGianhapsanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham).getGianhap());
						ctdh.setGiotao(date);
						Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
						ctdh.setIdNguoidung(ng);
						ctdh.setNgaytao(date);
						ctdh.setSoluong(soLuong);
						ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
						ctdh.setStatus(1);
						chiTietDonHangServiceImpl.save(ctdh);
						ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
						return "redirect:/banhang/giohang";
					} else {
						Date date = new Date();
						Chitietdonhang ctdh = new Chitietdonhang();
						ctdh.setGiasanpham(sp.getGiaban());
						ctdh.setGianhapsanpham(sp.getGianhap());
						ctdh.setGiotao(date);
						Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
						ctdh.setIdNguoidung(ng);
						ctdh.setNgaytao(date);
						ctdh.setSoluong(soLuong);
						ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(idSanPham));
						ctdh.setStatus(1);
						chiTietDonHangServiceImpl.save(ctdh);
						ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
						return "redirect:/banhang/giohang";
					}
				}
			}
		}
	}

	@RequestMapping(value = "/banhang/themmotsanphamvaogiohang/{id}")
	public String themMotSanPhamVaoGioHang(@PathVariable("id") int id, Model model,
			@PageableDefault(size = 12) Pageable pageable, HttpServletRequest request, RedirectAttributes ra) {
		Principal principal = request.getUserPrincipal();
		int idNguoiDung = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName()).getId();
		Chitietdonhang ctdh1 = chiTietDonHangServiceImpl.findAllByGioHang(idNguoiDung, id);
		Sanpham sp = sanPhamServiceImpl.findByIdSanPham(id);
		Integer tongNhap = nhapSanPhamServiceImpl.groupBy(id);

		Integer tongBan = null;
		tongBan = chiTietDonHangServiceImpl.groupBy(id);

		if (tongBan == null && tongNhap != null) {
			if (ctdh1 != null) {
				Chitietdonhang ctdh = new Chitietdonhang();
				ctdh.setId(ctdh1.getId());
				ctdh.setGiasanpham(ctdh1.getGiasanpham());
				ctdh.setGianhapsanpham(ctdh1.getGianhapsanpham());
				ctdh.setGiotao(ctdh1.getGiotao());
				ctdh.setIdNguoidung(ctdh1.getIdNguoidung());
				ctdh.setNgaytao(ctdh1.getNgaytao());
				ctdh.setSoluong(ctdh1.getSoluong() + 1);
				ctdh.setIdSanpham(ctdh1.getIdSanpham());
				ctdh.setStatus(1);
				chiTietDonHangServiceImpl.save(ctdh);
				ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
				return "redirect:/banhang/giohang";
			} else {
				if (sp.getGiamgia() != 0) {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban() - (sp.getGiaban() - sp.getGiamgia()));
					ctdh.setGianhapsanpham(sanPhamServiceImpl.findByIdSanPham(id).getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(1);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(id));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				} else {
					Date date = new Date();
					Chitietdonhang ctdh = new Chitietdonhang();
					ctdh.setGiasanpham(sp.getGiaban());
					ctdh.setGianhapsanpham(sp.getGianhap());
					ctdh.setGiotao(date);
					Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
					ctdh.setIdNguoidung(ng);
					ctdh.setNgaytao(date);
					ctdh.setSoluong(1);
					ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(id));
					ctdh.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				}
			}
		} else {
			Integer conLai = tongNhap - tongBan;
			if (conLai < 1) {
				ra.addFlashAttribute("status", "Số lượng tồn kho không đủ");
				return "redirect:/banhang/giohang";
			} else {
				if (ctdh1 != null) {
					ctdh1.setGiasanpham(ctdh1.getGiasanpham());
					ctdh1.setGianhapsanpham(ctdh1.getGianhapsanpham());
					ctdh1.setGiotao(ctdh1.getGiotao());
					ctdh1.setIdNguoidung(ctdh1.getIdNguoidung());
					ctdh1.setNgaytao(ctdh1.getNgaytao());
					ctdh1.setSoluong(ctdh1.getSoluong() + 1);
					ctdh1.setIdSanpham(ctdh1.getIdSanpham());
					ctdh1.setStatus(1);
					chiTietDonHangServiceImpl.save(ctdh1);
					ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
					return "redirect:/banhang/giohang";
				} else {
					if (sp.getGiamgia() != 0) {
						Date date = new Date();
						Chitietdonhang ctdh = new Chitietdonhang();
						ctdh.setGiasanpham(sp.getGiaban() - (sp.getGiaban() - sp.getGiamgia()));
						ctdh.setGianhapsanpham(sanPhamServiceImpl.findByIdSanPham(id).getGianhap());
						ctdh.setGiotao(date);
						Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
						ctdh.setIdNguoidung(ng);
						ctdh.setNgaytao(date);
						ctdh.setSoluong(1);
						ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(id));
						ctdh.setStatus(1);
						chiTietDonHangServiceImpl.save(ctdh);
						return "redirect:/banhang/giohang";
					} else {
						Date date = new Date();
						Chitietdonhang ctdh = new Chitietdonhang();
						ctdh.setGiasanpham(sp.getGiaban());
						ctdh.setGianhapsanpham(sp.getGianhap());
						ctdh.setGiotao(date);
						Nguoidung ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
						ctdh.setIdNguoidung(ng);
						ctdh.setNgaytao(date);
						ctdh.setSoluong(1);
						ctdh.setIdSanpham(sanPhamServiceImpl.findByIdSanPham(id));
						ctdh.setStatus(1);
						chiTietDonHangServiceImpl.save(ctdh);
						ra.addFlashAttribute("status", "Cập nhật giỏ hàng thành công");
						return "redirect:/banhang/giohang";
					}
				}
			}
		}

	}
}
