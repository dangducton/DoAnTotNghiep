package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Nhacungcap;

public interface NhaCungCapService {
	Iterable<Nhacungcap> findAll();

	Optional<Nhacungcap> findById(Integer id);

	void save(Nhacungcap danhMuc);

	void delete(Integer id);
	
	Page<Nhacungcap> findAllDanhMuc(Pageable pageable);
	
	Nhacungcap findByIdDanhMuc(Integer id);
	
	List<Nhacungcap> findByIdDanhMucActive();
	
	List<Nhacungcap> findAllByTenDanhMuc(String tenDanhMuc);
}
