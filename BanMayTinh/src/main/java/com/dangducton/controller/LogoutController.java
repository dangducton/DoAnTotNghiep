package com.dangducton.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LogoutController {
	@GetMapping(value = "/logout")
	public String logoutSuccess(Model model,HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    ra.addFlashAttribute("errormessage", "Bạn đã đăng xuất");
		return "redirect:/trangdangnhap";
	}
}
