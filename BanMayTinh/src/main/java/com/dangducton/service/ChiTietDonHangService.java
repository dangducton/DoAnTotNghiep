package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Chitietdonhang;

public interface ChiTietDonHangService {
	Iterable<Chitietdonhang> findAll();

	Optional<Chitietdonhang> findById(Integer id);

	void save(Chitietdonhang danhMuc);

	void delete(Integer id);
	
	Page<Chitietdonhang> findAllChiTietDonHang(Pageable pageable);
	
	Chitietdonhang findByIdChiTietDonHang(Integer id);
	
	List<Chitietdonhang> findByIdChiTietDonHangActive();
	
	List<Chitietdonhang> findAllByGioHang(Integer id);
	
	Chitietdonhang findAllByGioHang(Integer id, Integer idSanpham);
	
	public Integer groupBy(Integer id);
	
	public Integer count(Integer id);
	
	List<Chitietdonhang> findAllGioHangTheoDonHang(Integer id);
	
	public long countchitietdonhang();
	
	Page<Chitietdonhang> findAllSanPhamBanChay(Pageable pageable);
	
	public int tinhSoLuongSanPham(Integer id);
	
	List<Chitietdonhang> findAllSanPhamBanChayTheoNgay(Integer nam, Integer thang,Integer ngay);
	
	public int tinhSoLuongSanPhamTheoNgay(Integer id,Integer nam, Integer thang,Integer ngay);
	
	List<Chitietdonhang> findAllSanPhamBanChayTheoThang(Integer nam, Integer thang);
	
	public int tinhSoLuongSanPhamTheoThang(Integer id,Integer nam, Integer thang);
	
	List<Chitietdonhang> findAllSanPhamBanChayTheoNam(Integer nam);
	
	public int tinhSoLuongSanPhamTheoNam(Integer id,Integer nam);
	
	List<Chitietdonhang> tinhDoanhThuTheoNgay(Integer nam, Integer thang,Integer ngay);
	
	List<Chitietdonhang> tinhDoanhThuTheoThang(Integer nam, Integer thang);
	
	List<Chitietdonhang> tinhDoanhThuTheoNam(Integer nam);
	
}
