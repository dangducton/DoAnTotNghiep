package com.dangducton.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Lienhe;
import com.dangducton.repository.LienHeRespository;

@Service
@Transactional
public class LienHeServiceImpl implements LienHeService{
	@Autowired(required = true)
	public LienHeRespository lienHeRespository;

	@Override
	public Iterable<Lienhe> findAll() {
		// TODO Auto-generated method stub
		return lienHeRespository.findAll();
	}

	@Override
	public Optional<Lienhe> findById(Integer id) {
		// TODO Auto-generated method stub
		return lienHeRespository.findById(id);
	}

	@Override
	public void save(Lienhe lienhe) {
		// TODO Auto-generated method stub
		lienHeRespository.save(lienhe);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		lienHeRespository.deleteById(id);
	}

	@Override
	public Lienhe findByIdLienHe(Integer id) {
		// TODO Auto-generated method stub
		return lienHeRespository.findByIdLienHe(id);
	}

	@Override
	public Page<Lienhe> findAllLienHe(Pageable pageable) {
		// TODO Auto-generated method stub
		return lienHeRespository.findAllLienHe(pageable);
	}

	@Override
	public int findMaxID() {
		// TODO Auto-generated method stub
		return lienHeRespository.findMaxID();
	}

	@Override
	public long countLienHe() {
		// TODO Auto-generated method stub
		return lienHeRespository.countLienHe();
	}

}
