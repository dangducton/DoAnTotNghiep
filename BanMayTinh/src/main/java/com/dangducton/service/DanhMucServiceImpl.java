package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Danhmuc;
import com.dangducton.repository.DanhMucRepository;

@Service
@Transactional
public class DanhMucServiceImpl implements DanhMucService{
	
	@Autowired(required = true)
	public DanhMucRepository danhMucRepository;

	@Override
	public Iterable<Danhmuc> findAll() {
		// TODO Auto-generated method stub
		return danhMucRepository.findAll();
	}

	@Override
	public Optional<Danhmuc> findById(Integer id) {
		// TODO Auto-generated method stub
		return danhMucRepository.findById(id);
	}

	@Override
	public void save(Danhmuc danhMuc) {
		// TODO Auto-generated method stub
		danhMucRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		danhMucRepository.deleteById(id);
	}

	@Override
	public Page<Danhmuc> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return danhMucRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Danhmuc findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return danhMucRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Danhmuc> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return danhMucRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Danhmuc> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return danhMucRepository.findAllByTenDanhMuc(tenDanhMuc);
	}
}
