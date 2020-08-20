package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import com.dangducton.entities.Sanphamyeuthich;


public interface SanPhamYeuThichService {
	Iterable<Sanphamyeuthich> findAll();

	Optional<Sanphamyeuthich> findById(Integer id);

	void save(Sanphamyeuthich sanPham);

	void delete(Integer id);
	
	List<Sanphamyeuthich> findAllByNguoiDung(Integer id);
	
	Sanphamyeuthich findAllBySanPhamYeuThich(Integer id, Integer idSanpham);
	
	public Integer count(Integer id);
	
	public long countsanphamyeuthich();
}
