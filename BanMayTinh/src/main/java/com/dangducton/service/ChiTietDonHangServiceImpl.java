package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Chitietdonhang;
import com.dangducton.repository.ChiTietDonHangRepository;

@Service
@Transactional
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService{
	@Autowired(required = true)
	public ChiTietDonHangRepository chiTietDonHangRepository;

	@Override
	public Iterable<Chitietdonhang> findAll() {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAll();
	}

	@Override
	public Optional<Chitietdonhang> findById(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findById(id);
	}

	@Override
	public void save(Chitietdonhang danhMuc) {
		// TODO Auto-generated method stub
		chiTietDonHangRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		chiTietDonHangRepository.deleteById(id);
	}

	@Override
	public Page<Chitietdonhang> findAllChiTietDonHang(Pageable pageable) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllChiTietDonHang(pageable);
	}

	@Override
	public Chitietdonhang findByIdChiTietDonHang(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findByIdChiTietDonHang(id);
	}

	@Override
	public List<Chitietdonhang> findByIdChiTietDonHangActive() {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findByIdChiTietDonHangActive();
	}

	@Override
	public List<Chitietdonhang> findAllByGioHang(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllByGioHang(id);
	}

	@Override
	public Chitietdonhang findAllByGioHang(Integer id, Integer idSanpham) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllByGioHang(id, idSanpham);
	}

	@Override
	public Integer groupBy(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.groupBy(id);
	}

	@Override
	public Integer count(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.count(id);
	}

	@Override
	public List<Chitietdonhang> findAllGioHangTheoDonHang(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllGioHangTheoDonHang(id);
	}

	@Override
	public long countchitietdonhang() {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.countchitietdonhang();
	}

	@Override
	public Page<Chitietdonhang> findAllSanPhamBanChay(Pageable pageable) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllSanPhamBanChay(pageable);
	}

	@Override
	public int tinhSoLuongSanPham(Integer id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhSoLuongSanPham(id);
	}

	@Override
	public List<Chitietdonhang> findAllSanPhamBanChayTheoNgay(Integer nam, Integer thang, Integer ngay) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllSanPhamBanChayTheoNgay(nam, thang, ngay);
	}

	@Override
	public int tinhSoLuongSanPhamTheoNgay(Integer id, Integer nam, Integer thang, Integer ngay) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhSoLuongSanPhamTheoNgay(id, nam, thang, ngay);
	}

	@Override
	public List<Chitietdonhang> findAllSanPhamBanChayTheoThang(Integer nam, Integer thang) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllSanPhamBanChayTheoThang(nam, thang);
	}

	@Override
	public int tinhSoLuongSanPhamTheoThang(Integer id, Integer nam, Integer thang) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhSoLuongSanPhamTheoThang(id, nam, thang);
	}

	@Override
	public List<Chitietdonhang> findAllSanPhamBanChayTheoNam(Integer nam) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAllSanPhamBanChayTheoNam(nam);
	}

	@Override
	public int tinhSoLuongSanPhamTheoNam(Integer id, Integer nam) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhSoLuongSanPhamTheoNam(id, nam);
	}

	@Override
	public List<Chitietdonhang> tinhDoanhThuTheoNgay(Integer nam, Integer thang, Integer ngay) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhDoanhThuTheoNgay(nam, thang, ngay);
	}

	@Override
	public List<Chitietdonhang> tinhDoanhThuTheoThang(Integer nam, Integer thang) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhDoanhThuTheoThang(nam, thang);
	}

	@Override
	public List<Chitietdonhang> tinhDoanhThuTheoNam(Integer nam) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.tinhDoanhThuTheoNam(nam);
	}


}
