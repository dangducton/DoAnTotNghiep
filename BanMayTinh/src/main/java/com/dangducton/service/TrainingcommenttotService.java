package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Trainingcommenttot;

public interface TrainingcommenttotService {
	Iterable<Trainingcommenttot> findAll();

	Optional<Trainingcommenttot> findById(Integer id);

	void save(Trainingcommenttot danhMuc);

	void delete(Integer id);
	
	Page<Trainingcommenttot> findAllDanhMuc(Pageable pageable);
	
	Trainingcommenttot findByIdDanhMuc(Integer id);
	
	List<Trainingcommenttot> findByIdDanhMucActive();
	
	List<Trainingcommenttot> findAllByTenDanhMuc(String tenDanhMuc);
	
	List<Trainingcommenttot> findAll1();
}
