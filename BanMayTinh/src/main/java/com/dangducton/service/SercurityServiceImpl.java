package com.dangducton.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dangducton.entities.Role;

@Service
@Transactional
public class SercurityServiceImpl implements SercurityService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void autoLogin(String email, String password, Collection<Role> collection, HttpServletRequest request) {
		if (StringUtils.hasText(password)) {
			UserDetails details = userDetailsService.loadUserByUsername(email);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password, details.getAuthorities()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + collection));
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

	}
	@Override
	public void autoLoginFB(String email, String password,String collection, HttpServletRequest request) {
		if (StringUtils.hasText(password)) {
			UserDetails details = userDetailsService.loadUserByUsername(email);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password, details.getAuthorities()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}else {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(collection));
			Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, grantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

	}
}
