package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Thanhtoan;

public interface ThanhToanService {
	Iterable<Thanhtoan> findAll();

	Optional<Thanhtoan> findById(Integer id);

	void save(Thanhtoan danhMuc);

	void delete(Integer id);
	
	Page<Thanhtoan> findAllDanhMuc(Pageable pageable);
	
	Thanhtoan findByIdDanhMuc(Integer id);
	
	List<Thanhtoan> findByIdDanhMucActive();
	
	List<Thanhtoan> findAllByTenDanhMuc(String tenDanhMuc);
}
