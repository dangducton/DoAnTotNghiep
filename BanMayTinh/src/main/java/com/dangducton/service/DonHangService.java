package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Donhang;

public interface DonHangService {
	Iterable<Donhang> findAll();

	Optional<Donhang> findById(Integer id);

	void save(Donhang danhMuc);

	void delete(Integer id);
	
	Page<Donhang> findAllDanhMuc(Pageable pageable);
	
	Donhang findByIdDanhMuc(Integer id);
	
	List<Donhang> findByIdDanhMucActive();
	
	int findMaxID();
	
	Page<Donhang> findByIdDonHangByNguoiDung(Integer id,Pageable pageable);
	
	public long countdonhang();
	
	public long countdonhangmoi();
	
	List<Donhang> findByListDonHangMoi();
	
	Page<Donhang> findDonHangByTrangThaiDonHang(Integer id,Pageable pageable);
	
	Page<Donhang> findAllByTen(String tenSanPham,Pageable pageable);
}
