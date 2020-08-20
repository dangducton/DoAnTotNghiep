package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Vanchuyen;

public interface VanChuyenService {
	Iterable<Vanchuyen> findAll();

	Optional<Vanchuyen> findById(Integer id);

	void save(Vanchuyen danhMuc);

	void delete(Integer id);
	
	Page<Vanchuyen> findAllDanhMuc(Pageable pageable);
	
	Vanchuyen findByIdDanhMuc(Integer id);
	
	List<Vanchuyen> findByIdDanhMucActive();
	
	List<Vanchuyen> findAllByTenDanhMuc(String tenDanhMuc);
}
