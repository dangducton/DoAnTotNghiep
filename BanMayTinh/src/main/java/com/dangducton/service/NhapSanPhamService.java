package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Nhapsanpham;

public interface NhapSanPhamService {
	Iterable<Nhapsanpham> findAll();

	Optional<Nhapsanpham> findById(Integer id);

	void save(Nhapsanpham danhMuc);

	void delete(Integer id);
	
	public Integer groupBy(Integer id);
	
	Page<Nhapsanpham> findAllByNhapSanPham(Pageable pageable);
	
	List<Nhapsanpham> findAllByNhapSanPhamTinhTienNhap();
}
