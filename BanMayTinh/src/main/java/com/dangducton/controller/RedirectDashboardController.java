package com.dangducton.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectDashboardController {

	@RequestMapping(value = "/redirectdashboard", method = { RequestMethod.POST, RequestMethod.GET })
	public String redirectdashboard(Principal principal, SecurityContextHolder auth) {

		String redirecturl = "";
		Collection<? extends GrantedAuthority> granted = auth.getContext().getAuthentication().getAuthorities();
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		// set page default to rules common
		for (int i = 0; i < granted.size(); i++) {
			name = granted.toArray()[i] + "";
			System.out.println("role verified" + i + " is -> " + name);

		}

		if (name.equalsIgnoreCase("ROLE_ADMIN")) {
			redirecturl = "redirect:/admin/home";
		} else if (name.equalsIgnoreCase("ROLE_USER")) {
			redirecturl = "redirect:/";
		}else if (name.equalsIgnoreCase("ROLE_SUPPER")) {
			redirecturl = "redirect:/admin/home";
		}
		System.out.println(principal.getName());

		return redirecturl;
	}
}