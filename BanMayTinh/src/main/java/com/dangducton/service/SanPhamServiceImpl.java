package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Sanpham;
import com.dangducton.repository.SanPhamRepository;

@Service
@Transactional
public class SanPhamServiceImpl implements SanPhamService{
	
	@Autowired(required = true)
	public SanPhamRepository sanPhamRepository;

	@Override
	public Iterable<Sanpham> findAll() {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAll();
	}

	@Override
	public Optional<Sanpham> findById(Integer id) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findById(id);
	}

	@Override
	public void save(Sanpham sanPham) {
		// TODO Auto-generated method stub
		sanPhamRepository.save(sanPham);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sanPhamRepository.deleteById(id);
	}

	@Override
	public Page<Sanpham> findAllSanPham(Pageable pageable) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAllSanPham(pageable);
	}

	@Override
	public Sanpham findByIdSanPham(Integer id) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByIdSanPham(id);
	}

	@Override
	public List<Sanpham> findByIdSanPhamActive() {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByIdSanPhamActive();
	}

	@Override
	public List<Sanpham> findAllByTenSanPham(String tenSanPham) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAllByTenSanPham(tenSanPham);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return sanPhamRepository.count();
	}

}
