package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Thanhtoan;
import com.dangducton.repository.ThanhToanRepository;

@Service
@Transactional
public class ThanhToanServiceImpl implements ThanhToanService{
	
	@Autowired(required = true)
	public ThanhToanRepository thanhToanRepository;

	@Override
	public Iterable<Thanhtoan> findAll() {
		// TODO Auto-generated method stub
		return thanhToanRepository.findAll();
	}

	@Override
	public Optional<Thanhtoan> findById(Integer id) {
		// TODO Auto-generated method stub
		return thanhToanRepository.findById(id);
	}

	@Override
	public void save(Thanhtoan danhMuc) {
		// TODO Auto-generated method stub
		thanhToanRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		thanhToanRepository.deleteById(id);
	}

	@Override
	public Page<Thanhtoan> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return thanhToanRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Thanhtoan findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return thanhToanRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Thanhtoan> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return thanhToanRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Thanhtoan> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return thanhToanRepository.findAllByTenDanhMuc(tenDanhMuc);
	}
}
