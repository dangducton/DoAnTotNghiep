package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Danhmuc;

public interface DanhMucService {
	
	Iterable<Danhmuc> findAll();

	Optional<Danhmuc> findById(Integer id);

	void save(Danhmuc danhMuc);

	void delete(Integer id);
	
	Page<Danhmuc> findAllDanhMuc(Pageable pageable);
	
	Danhmuc findByIdDanhMuc(Integer id);
	
	List<Danhmuc> findByIdDanhMucActive();
	
	List<Danhmuc> findAllByTenDanhMuc(String tenDanhMuc);
}

