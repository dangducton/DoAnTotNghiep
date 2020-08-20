package com.dangducton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Nguoidung;
import com.dangducton.dto.PdfUserDetails;
import com.dangducton.repository.NguoiDungRepository;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{
	@Autowired
	private NguoiDungRepository nguoiDungRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Nguoidung user = nguoiDungRepository.findByEmailAndEnabled(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found for "+email);
		}
		return new PdfUserDetails(user);
	}
	

}
