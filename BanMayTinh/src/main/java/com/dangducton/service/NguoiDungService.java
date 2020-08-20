package com.dangducton.service;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dangducton.entities.Nguoidung;

public interface NguoiDungService {
	Iterable<Nguoidung> findAll();

	Optional<Nguoidung> findById(Integer id);

	Nguoidung save(Nguoidung nguoiDung);

	Nguoidung findByEmail(String email);

	void update(Nguoidung dbNguoiDung);

	Nguoidung findByEmailAndEnabled(String email);

	Page<Nguoidung> findAllNguoiDung(Pageable pageable);

	Page<Nguoidung> findAllQuanTri(Pageable pageable);
	
	Nguoidung checkEmail(String email);
	
	public long countQuanTri();
	
	public long countNguoiDung();
	
	public boolean checkIfValidOldPassword(final String passWordPersent, final String oldPassword);
	
	public Nguoidung changeUserPassword(final Nguoidung nguoiDung, final String password);
}
