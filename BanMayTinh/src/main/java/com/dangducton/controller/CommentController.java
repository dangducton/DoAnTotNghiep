package com.dangducton.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.CommentDTO;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Comment;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Trainingcommenttot;
import com.dangducton.entities.Trainingcommentxau;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.TrainingcommenttotServiceImpl;
import com.dangducton.service.TrainingcommentxauServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class CommentController {
	@Autowired
	private CommentServiceImpl commentServiceImpl;

	@Autowired
	private TrainingcommenttotServiceImpl trainingcommenttotServiceImpl;

	@Autowired
	private TrainingcommentxauServiceImpl trainingcommentxauServiceImpl;

	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@GetMapping(value = { "/admin/danhgiacomment" })
	public String danhgiacomment(Model model, @ModelAttribute("status") String status,HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Đánh giá comment");
		model.addAttribute("status", status);
		Page<Comment> danTocPage = commentServiceImpl.findAllDanhMuc(pageable);
		Pagination<Comment> page = new Pagination<Comment>(danTocPage, "/admin/danhgiacomment");
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
		List<CommentDTO> listGioHang = new ArrayList<CommentDTO>();
		for (Comment cm12 : danTocPage) {
			double predict_neg_prob = negProb;
			double predict_pos_prob = posProb;
			String cm = cm12.getNoidung().toLowerCase();
			int found_neg = 0;
			int found_pos = 0;
			for (String keyword : dictionary) {
				if (cm.contains(keyword)) {
					System.out.println(cm);
					cm = cm.replace(keyword,"");
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
			String dudoan;
			if (found_neg > found_pos) {
				dudoan = "Dự đoán: Bình luận chê";
			} else if (found_neg < found_pos) {
				dudoan = "Dự đoán: Bình luận khen";
			} else {
				dudoan = "Dự đoán: Không xác định";
			}
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(cm12.getIdSanpham().getId());
			listGioHang.add(new CommentDTO(cm12.getId(), cm12.getIdSanpham().getTen(), asp.getUrlAnh(),
					cm12.getTennguoidung(), cm12.getEmail(), cm12.getNoidung(), found_pos, found_neg,
					dudoan, cm12.getNgaytao(), cm12.getStatus()));
		}

		model.addAttribute("listDanhMuc", listGioHang);
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

		return "admin/comment";
	}

	@GetMapping(path = "/admin/updateNotActivateComment/{id}")
	public String updateNotActivate(@PathVariable("id") Integer id, Comment dt, RedirectAttributes ra) {
		Comment cm = commentServiceImpl.findById(id).get();
		cm.setStatus(0);
		commentServiceImpl.save(cm);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhgiacomment";
	}

	@GetMapping(path = "/admin/updateActivateComment/{id}")
	public String updateActivate(@PathVariable("id") Integer id, Comment dt, RedirectAttributes ra) {
		Comment cm = commentServiceImpl.findById(id).get();
		cm.setStatus(1);
		commentServiceImpl.save(cm);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhgiacomment";
	}
}
