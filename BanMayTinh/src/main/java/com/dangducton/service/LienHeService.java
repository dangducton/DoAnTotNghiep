package com.dangducton.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Lienhe;

public interface LienHeService {
	Iterable<Lienhe> findAll();

	Optional<Lienhe> findById(Integer id);

	void save(Lienhe lienhe);

	void delete(Integer id);
	
	Lienhe findByIdLienHe(Integer id);
	
	Page<Lienhe> findAllLienHe(Pageable pageable);
	
	int findMaxID();
	
	public long countLienHe();
}
