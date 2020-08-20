package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.repository.AnhSanPhamRepository;

@Service
@Transactional
public class AnhSanPhamServiceImpl implements AnhSanPhamService {

	@Autowired(required = true)
	public AnhSanPhamRepository anhSanPhamRepository;

	@Override
	public Iterable<Anhsanpham> findAll() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAll();
	}

	@Override
	public Optional<Anhsanpham> findById(Integer id) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findById(id);
	}

	@Override
	public void save(Anhsanpham danhMuc) {
		// TODO Auto-generated method stub
		anhSanPhamRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		anhSanPhamRepository.deleteById(id);
	}

	@Override
	public Page<Anhsanpham> findAllAnhSanPham(Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllAnhSanPham(pageable);
	}

	@Override
	public Anhsanpham findByIdAnhSanPham(Integer id) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findByIdAnhSanPham(id);
	}

	@Override
	public List<Anhsanpham> findByIdAnhSanPhamActive() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findByIdAnhSanPhamActive();
	}

	@Override
	public List<Anhsanpham> findByIdAnhSanPhamIdSanPham(Integer id) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findByIdAnhSanPhamIdSanPham(id);
	}

	@Override
	public List<Anhsanpham> findByIdAnhSanPhamGruopByIdSanPham() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Anhsanpham> findAllSanPhamTheoDanhMuc(Integer id, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllSanPhamTheoDanhMuc(id, pageable);
	}

	@Override
	public Page<Anhsanpham> findAllByTenSanPham(String tenSanPham, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllByTenSanPham(tenSanPham, pageable);
	}

	@Override
	public Page<Anhsanpham> findAllSanPhamTheoDanhMucActive(Integer id, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllSanPhamTheoDanhMucActive(id, pageable);
	}

	@Override
	public Page<Anhsanpham> findAllBySanPhamActive(Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllBySanPhamActive(pageable);
	}

	@Override
	public List<Anhsanpham> findAllByAnhSanPhamTheoSanPham() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllByAnhSanPhamTheoSanPham();
	}

	@Override
	public Anhsanpham findAllByAnhSanPhamTheoSanPham(Integer id) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllByAnhSanPhamTheoSanPham(id);
	}

	@Override
	public Page<Anhsanpham> findAllByGia(int giabdatdau, int giaketthuc, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllByGia(giabdatdau, giaketthuc, pageable);
	}

	@Override
	public List<Anhsanpham> findAllSanPhamMuaNhieuNhat() {
		return anhSanPhamRepository.findAllSanPhamMuaNhieuNhat();
	}

	@Override
	public Page<Anhsanpham> findAllByTenSanPhamActive(String tenSanPham, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllByTenSanPhamActive(tenSanPham, pageable);
	}

	@Override
	public Page<Anhsanpham> findAllBySanPhamTheoNhap(Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllBySanPhamTheoNhap(pageable);
	}

	@Override
	public List<Anhsanpham> findAllSanPhamMuaMoiNhat() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllSanPhamMuaMoiNhat();
	}

	@Override
	public List<Anhsanpham> findAllSanPhamSellNhieuNhap() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllSanPhamSellNhieuNhap();
	}

	@Override
	public Page<Anhsanpham> findAllSanPhamTheoNhaCungCapActive(Integer id, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findAllSanPhamTheoNhaCungCapActive(id, pageable);
	}

	@Override
	public List<Anhsanpham> findByIdAnhSanPhamActive1() {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findByIdAnhSanPhamActive1();
	}

	@Override
	public Anhsanpham findSanPhamByChiTietDonHang(Integer id) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.findSanPhamByChiTietDonHang(id);
	}

	@Override
	public Page<Anhsanpham> timKiemTheoDanhMucNhaCungCapGiaSanPham(Integer idDanhMuc, Integer idNhaCungCap,
			Integer giaBatDau, Integer giaKetThuc, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.timKiemTheoDanhMucNhaCungCapGiaSanPham(idDanhMuc, idNhaCungCap, giaBatDau,
				giaKetThuc, pageable);
	}

	@Override
	public Page<Anhsanpham> timKiemTheoDanhMucNhaCungCap(Integer idDanhMuc, Integer idNhaCungCap, Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.timKiemTheoDanhMucNhaCungCap(idDanhMuc, idNhaCungCap, pageable);
	}

	@Override
	public Page<Anhsanpham> timKiemTheoDanhMucGiaSanPham(Integer idDanhMuc, Integer giaBatDau, Integer giaKetThuc,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.timKiemTheoDanhMucGiaSanPham(idDanhMuc, giaBatDau, giaKetThuc, pageable);
	}

	@Override
	public Page<Anhsanpham> timKiemTheoNhaCungCapGiaSanPham(Integer idNhaCungCap, Integer giaBatDau, Integer giaKetThuc,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return anhSanPhamRepository.timKiemTheoNhaCungCapGiaSanPham(idNhaCungCap, giaBatDau, giaKetThuc, pageable);
	}

}
