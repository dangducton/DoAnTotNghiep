package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Nhapsanpham;
import com.dangducton.repository.NhapSanPhamRepository;

@Service
@Transactional
public class NhapSanPhamServiceImpl implements NhapSanPhamService{
	@Autowired
	private NhapSanPhamRepository nhapSanPhamRepository;

	@Override
	public Iterable<Nhapsanpham> findAll() {
		// TODO Auto-generated method stub
		return nhapSanPhamRepository.findAll();
	}

	@Override
	public Optional<Nhapsanpham> findById(Integer id) {
		// TODO Auto-generated method stub
		return nhapSanPhamRepository.findById(id);
	}

	@Override
	public void save(Nhapsanpham danhMuc) {
		// TODO Auto-generated method stub
		nhapSanPhamRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		nhapSanPhamRepository.deleteById(id);
	}

	@Override
	public Integer groupBy(Integer id) {
		// TODO Auto-generated method stub
		return nhapSanPhamRepository.groupBy(id);
	}

	@Override
	public Page<Nhapsanpham> findAllByNhapSanPham(Pageable pageable) {
		return nhapSanPhamRepository.findAllByNhapSanPham(pageable);
	}

	@Override
	public List<Nhapsanpham> findAllByNhapSanPhamTinhTienNhap() {
		// TODO Auto-generated method stub
		return nhapSanPhamRepository.findAllByNhapSanPhamTinhTienNhap();
	}
}
