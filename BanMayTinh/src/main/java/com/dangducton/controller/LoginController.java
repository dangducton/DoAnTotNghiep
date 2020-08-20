package com.dangducton.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Role;
import com.dangducton.dto.NguoiDungSignUpDTO;
import com.dangducton.repository.RoleRespository;
import com.dangducton.service.NguoiDungService;
import com.dangducton.service.SercurityService;

@Controller
public class LoginController {
	@Autowired
	private RoleRespository roleRespository;

	@Autowired
	private NguoiDungService userService;

	@Autowired
	private SercurityService sercurityService;

	@GetMapping("/trangdangnhap")
	public String trangDangNhap(Model model, @ModelAttribute("errormessage") String status) {
		model.addAttribute("title", "Đăng nhập");
		model.addAttribute("errormessage", status);
		return "frontend/dangnhap";
	}

	@GetMapping("/trangdangky")
	public String trangDangKy(Model model, @ModelAttribute("errormessage") String status) {
		model.addAttribute("title", "Đăng ký");
		model.addAttribute("nguoiDungSignUpDTO", new NguoiDungSignUpDTO());
		model.addAttribute("errormessage", status);
		return "frontend/dangky";
	}

	@GetMapping(value = "/loginfailure")
	public String loginfailure(Model model,RedirectAttributes ra) {
		ra.addFlashAttribute("errormessage", "Tài khoản và mật khẩu không chính xác");
		return "redirect:/trangdangnhap";
	}

	@GetMapping(value = "/accessdenied")
	public String accessdenied(Model model,RedirectAttributes ra) {
		ra.addFlashAttribute("errormessage", "Không đủ quyền try cập");
		return "redirect:/trangdangnhap";
	}

	@PostMapping(value = "/register")
	public String registration(@ModelAttribute NguoiDungSignUpDTO nguoiDungSignUpDTO, HttpServletRequest request,
			Model model, RedirectAttributes ra) {
		String hoten = nguoiDungSignUpDTO.getHoten();
		String confirmpassword = nguoiDungSignUpDTO.getConfirmpassword();
		String password = nguoiDungSignUpDTO.getPassword();
		Nguoidung dbUser = userService.findByEmailAndEnabled(nguoiDungSignUpDTO.getEmail());
		if (dbUser == null) {
			Nguoidung nd = new Nguoidung();
			if (confirmpassword.equals(password) == true) {
				if (hoten.length() >= 5) {
					if (confirmpassword.length() >= 8 && password.length() >= 8) {
						Role role = roleRespository.findByName("ROLE_USER");
						if (role == null) {
							role = new Role();
							role.setTen("ROLE_USER");
							role.setStatus(1);
							roleRespository.save(role);

						}
						nd.setRoleCollection(Arrays.asList(role));
						nd.setHoten(nguoiDungSignUpDTO.getHoten());
						nd.setPassword(password);
						nd.setEmail(nguoiDungSignUpDTO.getEmail());

						userService.save(nd);
						sercurityService.autoLogin(nd.getEmail(), password, nd.getRoleCollection(), request);
						return "redirect:/redirectdashboard";
					} else {
						ra.addFlashAttribute("errormessage", "Mật khẩu gồm ít nhất 8 ký tự");
						return "redirect:/trangdangky";
					}
				} else {
					ra.addFlashAttribute("errormessage", "Yêu cầu nhập đầy đủ họ tên");
					return "redirect:/trangdangky";
				}
			} else {
				ra.addFlashAttribute("errormessage", "Yêu cầu nhập lại mật khẩu");
				return "redirect:/trangdangky";
			}
		} else {
			ra.addFlashAttribute("errormessage", "Email này đã được đăng ký");
			return "redirect:/trangdangky";
		}

	}
}
