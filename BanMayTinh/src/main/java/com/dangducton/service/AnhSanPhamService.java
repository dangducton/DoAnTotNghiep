package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Anhsanpham;

public interface AnhSanPhamService {
	Iterable<Anhsanpham> findAll();

	Optional<Anhsanpham> findById(Integer id);

	void save(Anhsanpham anhSanPham);

	void delete(Integer id);
	
	Page<Anhsanpham> findAllAnhSanPham(Pageable pageable);
	
	Anhsanpham findByIdAnhSanPham(Integer id);
	
	List<Anhsanpham> findByIdAnhSanPhamActive();
	
	List<Anhsanpham> findByIdAnhSanPhamGruopByIdSanPham();
	
	List<Anhsanpham> findByIdAnhSanPhamIdSanPham(Integer id);
	
	Page<Anhsanpham> findAllSanPhamTheoDanhMuc(Integer id,Pageable pageable);
	
	Page<Anhsanpham> findAllByTenSanPham(String tenSanPham,Pageable pageable);
	
	Page<Anhsanpham> findAllSanPhamTheoDanhMucActive(Integer id,Pageable pageable);
	
	Page<Anhsanpham> findAllBySanPhamActive(Pageable pageable);
	
	List<Anhsanpham> findAllByAnhSanPhamTheoSanPham();
	
	Anhsanpham findAllByAnhSanPhamTheoSanPham(Integer id);
	
	Page<Anhsanpham> findAllByGia(int giabdatdau,int giaketthuc,Pageable pageable);
	
	List<Anhsanpham> findAllSanPhamMuaNhieuNhat();
	
	Page<Anhsanpham> findAllByTenSanPhamActive(String tenSanPham,Pageable pageable);
	
	Page<Anhsanpham> findAllBySanPhamTheoNhap(Pageable pageable);
	
	List<Anhsanpham> findAllSanPhamMuaMoiNhat();
	
	List<Anhsanpham> findAllSanPhamSellNhieuNhap();
	
	Page<Anhsanpham> findAllSanPhamTheoNhaCungCapActive(Integer id,Pageable pageable);
	
	List<Anhsanpham> findByIdAnhSanPhamActive1();
	
	Anhsanpham findSanPhamByChiTietDonHang(Integer id);
	
	Page<Anhsanpham> timKiemTheoDanhMucNhaCungCapGiaSanPham(Integer idDanhMuc,Integer idNhaCungCap,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
	
	Page<Anhsanpham> timKiemTheoDanhMucNhaCungCap(Integer idDanhMuc,Integer idNhaCungCap,Pageable pageable);
	
	Page<Anhsanpham> timKiemTheoDanhMucGiaSanPham(Integer idDanhMuc,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
	
	Page<Anhsanpham> timKiemTheoNhaCungCapGiaSanPham(Integer idNhaCungCap,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
}
