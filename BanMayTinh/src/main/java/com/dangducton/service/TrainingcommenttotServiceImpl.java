package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Trainingcommenttot;
import com.dangducton.repository.TrainingcommenttotRepository;

@Service
@Transactional
public class TrainingcommenttotServiceImpl implements TrainingcommenttotService{
	@Autowired(required = true)
	public TrainingcommenttotRepository trainingcommenttotRepository;

	@Override
	public Iterable<Trainingcommenttot> findAll() {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findAll();
	}

	@Override
	public Optional<Trainingcommenttot> findById(Integer id) {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findById(id);
	}

	@Override
	public void save(Trainingcommenttot danhMuc) {
		// TODO Auto-generated method stub
		trainingcommenttotRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		trainingcommenttotRepository.deleteById(id);
	}

	@Override
	public Page<Trainingcommenttot> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findAllDanhMuc(pageable);
	}

	@Override
	public Trainingcommenttot findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Trainingcommenttot> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Trainingcommenttot> findAllByTenDanhMuc(String tenDanhMuc) {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findAllByTenDanhMuc(tenDanhMuc);
	}

	@Override
	public List<Trainingcommenttot> findAll1() {
		// TODO Auto-generated method stub
		return trainingcommenttotRepository.findAll();
	}

}
