package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Vanchuyen;
import com.dangducton.repository.VanChuyenRepository;

@Service
@Transactional
public class VanChuyenServiceImpl implements VanChuyenService{
	@Autowired(required = true)
	public VanChuyenRepository vanChuyenRepository;

	@Override
	public Iterable<Vanchuyen> findAll() {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findAll();
	}

	@Override
	public Optional<Vanchuyen> findById(Integer id) {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findById(id);
	}

	@Override
	public void save(Vanchuyen danhMuc) {
		// TODO Auto-generated method stub
		vanChuyenRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		vanChuyenRepository.deleteById(id);
	}

	@Override
	public Page<Vanchuyen> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Vanchuyen findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Vanchuyen> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Vanchuyen> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return vanChuyenRepository.findAllByTenDanhMuc(tenDanhMuc);
	}
}
