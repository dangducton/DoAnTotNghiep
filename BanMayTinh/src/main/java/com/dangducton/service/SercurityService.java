package com.dangducton.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dangducton.entities.Role;

public  interface SercurityService {
	void autoLogin(String email, String password, Collection<Role> collection, HttpServletRequest request);
	public void autoLoginFB(String email, String password,String collection, HttpServletRequest request);

}
