package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Donhang;
import com.dangducton.repository.DonHangRepository;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService{
	@Autowired(required = true)
	public DonHangRepository donHangRepository;
	
	@Override
	public Iterable<Donhang> findAll() {
		// TODO Auto-generated method stub
		return donHangRepository.findAll();
	}

	@Override
	public Optional<Donhang> findById(Integer id) {
		// TODO Auto-generated method stub
		return donHangRepository.findById(id);
	}

	@Override
	public void save(Donhang danhMuc) {
		// TODO Auto-generated method stub
		donHangRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		donHangRepository.deleteById(id);
	}

	@Override
	public Page<Donhang> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return donHangRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Donhang findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return donHangRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Donhang> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return donHangRepository.findByIdDanhMucActive();
	}

	@Override
	public int findMaxID() {
		// TODO Auto-generated method stub
		return donHangRepository.findMaxID();
	}

	@Override
	public Page<Donhang> findByIdDonHangByNguoiDung(Integer id,Pageable pageable) {
		// TODO Auto-generated method stub
		return donHangRepository.findByIdDonHangByNguoiDung(id,pageable);
	}

	@Override
	public long countdonhang() {
		// TODO Auto-generated method stub
		return donHangRepository.countdonhang();
	}

	@Override
	public long countdonhangmoi() {
		// TODO Auto-generated method stub
		return donHangRepository.countdonhangmoi();
	}

	@Override
	public List<Donhang> findByListDonHangMoi() {
		// TODO Auto-generated method stub
		return donHangRepository.findByListDonHangMoi();
	}

	@Override
	public Page<Donhang> findDonHangByTrangThaiDonHang(Integer id, Pageable pageable) {
		// TODO Auto-generated method stub
		return donHangRepository.findDonHangByTrangThaiDonHang(id, pageable);
	}

	@Override
	public Page<Donhang> findAllByTen(String tenSanPham, Pageable pageable) {
		// TODO Auto-generated method stub
		return donHangRepository.findAllByTen(tenSanPham, pageable);
	}

}

