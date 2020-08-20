package com.dangducton.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.dangducton.entities.Nguoidung;
import com.dangducton.repository.NguoiDungRepository;
import com.dangducton.util.PassWordUtil;

@Service
@Transactional
public class NguoiDungServiceImpl  implements NguoiDungService{
	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Nguoidung save(Nguoidung nguoiDung) {
		Date d = new Date();
		nguoiDung.setNgaytao(d);
		nguoiDung.setStatus(1);
		if (StringUtils.hasText(nguoiDung.getPassword())) {
			nguoiDung.setPassword(PassWordUtil.getEncoderPassword(nguoiDung.getPassword()));
		}
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	public boolean checkIfValidOldPassword(final String passWordPersent, final String oldPassword) {
		return passwordEncoder.matches(oldPassword, passWordPersent);
	}

	@Override
	public Nguoidung changeUserPassword(final Nguoidung nguoiDung, final String password) {
		nguoiDung.setPassword(passwordEncoder.encode(password));
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	public Nguoidung findByEmail(String email) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findByEmail(email);
	}

	@Override
	public void update(Nguoidung dbNguoiDung) {
		// TODO Auto-generated method stub
		nguoiDungRepository.save(dbNguoiDung);
	}

	@Override
	public Nguoidung findByEmailAndEnabled(String email) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findByEmailAndEnabled(email);
	}

	@Override
	public Page<Nguoidung> findAllNguoiDung(Pageable pageable) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findAllNguoiDung(pageable);
	}

	@Override
	public Page<Nguoidung> findAllQuanTri(Pageable pageable) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findAllQuanTri(pageable);
	}

	@Override
	public Iterable<Nguoidung> findAll() {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findAll();
	}

	@Override
	public Optional<Nguoidung> findById(Integer id) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findById(id);
	}

	@Override
	public Nguoidung checkEmail(String email) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.checkEmail(email);
	}

	@Override
	public long countQuanTri() {
		// TODO Auto-generated method stub
		return nguoiDungRepository.countQuanTri();
	}

	@Override
	public long countNguoiDung() {
		// TODO Auto-generated method stub
		return nguoiDungRepository.countNguoiDung();
	}

}
