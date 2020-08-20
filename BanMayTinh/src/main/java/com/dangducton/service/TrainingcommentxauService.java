package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Trainingcommentxau;

public interface TrainingcommentxauService {
	Iterable<Trainingcommentxau> findAll();

	Optional<Trainingcommentxau> findById(Integer id);

	void save(Trainingcommentxau danhMuc);

	void delete(Integer id);
	
	Page<Trainingcommentxau> findAllDanhMuc(Pageable pageable);
	
	Trainingcommentxau findByIdDanhMuc(Integer id);
	
	List<Trainingcommentxau> findByIdDanhMucActive();
	
	List<Trainingcommentxau> findAllByTenDanhMuc(String tenDanhMuc);
	
	List<Trainingcommentxau> findAll1();
}
