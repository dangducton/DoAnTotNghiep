package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Tintuc;

public interface TinTucService {
	Iterable<Tintuc> findAll();

	Optional<Tintuc> findById(Integer id);

	void save(Tintuc danhMuc);

	void delete(Integer id);
	
	Page<Tintuc> findAllTinTuc(Pageable pageable);
	
	Tintuc findByIdTinTuc(Integer id);
	
	List<Tintuc> findByIdTinTucActive();
	
	Page<Tintuc> findAllTinTucActive(Pageable pageable);
	
	List<Tintuc> findAllTinTucMoiNhat();
	
	List<Tintuc> findAllTinTucRandom();
	Page<Tintuc> findAllByTenSanPham(String tenSanPham,Pageable pageable);
}
