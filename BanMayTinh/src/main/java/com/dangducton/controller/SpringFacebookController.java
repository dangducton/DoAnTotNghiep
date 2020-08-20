package com.dangducton.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Role;
import com.dangducton.repository.RoleRespository;
import com.dangducton.service.FacebookService;
import com.dangducton.service.NguoiDungService;
import com.dangducton.service.SercurityService;

@Controller
public class SpringFacebookController {
	@Autowired
	private FacebookService facebookService;

	@Autowired
	private NguoiDungService userService;

	@Autowired
	private SercurityService sercurityService;

	@Autowired
	private RoleRespository roleRespository;

	@GetMapping(value = "/facebooklogin")
	public RedirectView facebookLogin() {
		RedirectView redirectView = new RedirectView();
		String url = facebookService.facebookLogin();
		redirectView.setUrl(url);
		return redirectView;
	}

	@GetMapping(value = "/facebook")
	public String facebook(@RequestParam("code") String code) {
		String accessToken = facebookService.getFacebookAccessToken(code);
		return "redirect:/facebookprofiledata/" + accessToken;
	}

	@GetMapping(value = "/facebookprofiledata/{accessToken:.+}")
	public String facebookprofiledata(@PathVariable String accessToken, Model model, HttpServletRequest request) {
		User user = facebookService.getFacebookUserProfile(accessToken);
		Nguoidung dbUser = userService.findByEmail(user.getEmail());
		Role role = roleRespository.findByName("ROLE_USER");
		if (role == null) {
			role = new Role();
			role.setStatus(1);
			role.setTen("ROLE_USER");
			roleRespository.save(role);
		}
		if (dbUser != null) {
			dbUser.setAnh("https://graph.facebook.com/" + user.getId() + "/picture");
			dbUser.setHoten(user.getFirstName() + " " + user.getLastName());
			userService.update(dbUser);
			model.addAttribute("user", dbUser);
			sercurityService.autoLoginFB(user.getEmail(), null,"ROLE_USER", request);
		} else {
			Nguoidung userInfo = new Nguoidung();
			userInfo.setHoten(user.getFirstName() + " " + user.getLastName());
			userInfo.setAnh("https://graph.facebook.com/" + user.getId() + "/picture");
			userInfo.setEmail(user.getEmail());
			userInfo.setRoleCollection(Arrays.asList(role));
			userService.save(userInfo);
			sercurityService.autoLoginFB(user.getEmail(), null,"ROLE_USER",request);
		}
		return "redirect:/redirectdashboard";
	}
}
