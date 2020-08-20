package com.dangducton.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.dto.TonKhoDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Lienhe;
import com.dangducton.entities.Nguoidung;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.LienHeServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class LienHeController {

	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;

	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;

	@Autowired
	DonHangServiceImpl donHangServiceImpl;

	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;

	@Autowired
	private LienHeServiceImpl lienHeServiceImpl;

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping(value = "/admin/traloilienhe")
	public String guiNoiDungLienHe(@RequestParam(name = "email") String email,@RequestParam(name = "id") Integer id,
			@RequestParam(name = "hoten") String hoten, @RequestParam(name = "noidung") String noidung,
			@RequestParam(name = "noidungtraloi") String noidungtraloi, RedirectAttributes ra)
			throws MessagingException, IOException {
				Lienhe lh = lienHeServiceImpl.findById(id).get();
				lh.setStatus(1);
				lienHeServiceImpl.save(lh);
				MimeMessage msg = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(msg, true);
				helper.setTo(email);
				helper.setSubject("Thư trả lời liên hệ");
				helper.setText("<div>Xin chào: " + hoten
						+ "<br><h3><strong>"+noidungtraloi+"</strong></h3></div>", true);
				javaMailSender.send(msg);
				ra.addFlashAttribute("status", "Trả lời liên hệ thành công");
				
				return "redirect:/admin/lienhe";
	}

	@GetMapping(value = "/guilienhe")
	public String sendEmailWithAttachment(@RequestParam(name = "email") String email,
			@RequestParam(name = "hoten") String hoten, @RequestParam(name = "noidung") String noidung,
			RedirectAttributes ra) throws MessagingException, IOException {
		boolean ktEmail = validate(email);
		if (ktEmail == true) {
			if (hoten.length() != 0 && noidung.length() != 0) {
				Lienhe lh = new Lienhe();
				Date date = new Date();
				lh.setEmail(email);
				lh.setHoten(hoten);
				lh.setNgaytao(date);
				lh.setNoidung(noidung);
				lh.setStatus(0);
				lienHeServiceImpl.save(lh);
				MimeMessage msg = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(msg, true);
				helper.setTo(email);
				helper.setSubject("Cảm ơn bạn đã gửi lại yêu cầu liên hệ");
				helper.setText("<div>Xin chào: " + hoten
						+ "<br><h3><strong>Chúng tôi sẽ liên hệ với bạn sớm nhất!!!</strong></h3></div>", true);
				javaMailSender.send(msg);
				ra.addFlashAttribute("status", "Cảm ơn bạn đã liên hệ với chúng tôi");
				return "redirect:/";
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ thông tin");
				return "redirect:/";
			}
		} else {
			ra.addFlashAttribute("status", "Yêu cầu nhập đúng địa chỉ Email");
			return "redirect:/";
		}

	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	@GetMapping(value = { "/admin/lienhe" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("lienHe", "Liên hệ");
		model.addAttribute("status", status);
		Page<Lienhe> danTocPage = lienHeServiceImpl.findAllLienHe(pageable);
		Pagination<Lienhe> page = new Pagination<Lienhe>(danTocPage, "/admin/lienhe");
		model.addAttribute("listLienHe", page.getContent());
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
		return "admin/lienhe";
	}

	@GetMapping("/admin/findonelienhe")
	@ResponseBody
	public Optional<Lienhe> findById(Integer id) {
		return lienHeServiceImpl.findById(id);
	}

}
