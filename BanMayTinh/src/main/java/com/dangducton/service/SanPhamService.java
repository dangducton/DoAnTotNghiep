package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Sanpham;

public interface SanPhamService {
	Iterable<Sanpham> findAll();

	Optional<Sanpham> findById(Integer id);

	void save(Sanpham sanPham);

	void delete(Integer id);
	
	Page<Sanpham> findAllSanPham(Pageable pageable);
	
	Sanpham findByIdSanPham(Integer id);
	
	List<Sanpham> findByIdSanPhamActive();
	
	List<Sanpham> findAllByTenSanPham(String tenSanPham);
	
	public long count();
	
}
