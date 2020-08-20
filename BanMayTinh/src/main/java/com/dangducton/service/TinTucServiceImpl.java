package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Tintuc;
import com.dangducton.repository.TinTucRepository;

@Service
@Transactional
public class TinTucServiceImpl  implements TinTucService{
	
	@Autowired(required = true)
	public TinTucRepository tinTucRepository;

	@Override
	public Iterable<Tintuc> findAll() {
		// TODO Auto-generated method stub
		return tinTucRepository.findAll();
	}

	@Override
	public Optional<Tintuc> findById(Integer id) {
		// TODO Auto-generated method stub
		return tinTucRepository.findById(id);
	}

	@Override
	public void save(Tintuc danhMuc) {
		// TODO Auto-generated method stub
		tinTucRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		tinTucRepository.deleteById(id);
	}

	@Override
	public Page<Tintuc> findAllTinTuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return tinTucRepository.findAllTinTuc(pageable);
	}

	@Override
	public Tintuc findByIdTinTuc(Integer id) {
		// TODO Auto-generated method stub
		return tinTucRepository.findByIdTinTuc(id);
	}

	@Override
	public List<Tintuc> findByIdTinTucActive() {
		// TODO Auto-generated method stub
		return tinTucRepository.findByIdTinTucActive();
	}

	@Override
	public Page<Tintuc> findAllTinTucActive(Pageable pageable) {
		// TODO Auto-generated method stub
		return tinTucRepository.findAllTinTucActive(pageable);
	}

	@Override
	public List<Tintuc> findAllTinTucMoiNhat() {
		// TODO Auto-generated method stub
		return tinTucRepository.findAllTinTucMoiNhat();
	}

	@Override
	public List<Tintuc> findAllTinTucRandom() {
		// TODO Auto-generated method stub
		return tinTucRepository.findAllTinTucRandom();
	}

	@Override
	public Page<Tintuc> findAllByTenSanPham(String tenSanPham, Pageable pageable) {
		// TODO Auto-generated method stub
		return tinTucRepository.findAllByTenSanPham(tenSanPham, pageable);
	}


}
