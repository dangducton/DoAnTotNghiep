package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Trainingcommentxau;
import com.dangducton.repository.TrainingcommentxauRepository;

@Service
@Transactional
public class TrainingcommentxauServiceImpl implements TrainingcommentxauService{
	@Autowired(required = true)
	public TrainingcommentxauRepository trainingcommentxauRepository;

	@Override
	public Iterable<Trainingcommentxau> findAll() {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findAll();
	}

	@Override
	public Optional<Trainingcommentxau> findById(Integer id) {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findById(id);
	}

	@Override
	public void save(Trainingcommentxau danhMuc) {
		// TODO Auto-generated method stub
		trainingcommentxauRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		trainingcommentxauRepository.deleteById(id);
	}

	@Override
	public Page<Trainingcommentxau> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Trainingcommentxau findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Trainingcommentxau> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Trainingcommentxau> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findAllByTenDanhMuc(tenDanhMuc);
	}

	@Override
	public List<Trainingcommentxau> findAll1() {
		// TODO Auto-generated method stub
		return trainingcommentxauRepository.findAll();
	}
}
