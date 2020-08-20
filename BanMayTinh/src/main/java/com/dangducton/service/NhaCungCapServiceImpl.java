package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Nhacungcap;
import com.dangducton.repository.NhaCungCapRepository;

@Service
@Transactional
public class NhaCungCapServiceImpl  implements NhaCungCapService{
	@Autowired(required = true)
	public NhaCungCapRepository nhaCungCapRepository;

	@Override
	public Iterable<Nhacungcap> findAll() {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findAll();
	}

	@Override
	public Optional<Nhacungcap> findById(Integer id) {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findById(id);
	}

	@Override
	public void save(Nhacungcap danhMuc) {
		// TODO Auto-generated method stub
		nhaCungCapRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		nhaCungCapRepository.deleteById(id);
	}

	@Override
	public Page<Nhacungcap> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Nhacungcap findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Nhacungcap> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Nhacungcap> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return nhaCungCapRepository.findAllByTenDanhMuc(tenDanhMuc);
	}

}
