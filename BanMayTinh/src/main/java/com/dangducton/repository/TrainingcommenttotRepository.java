package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Trainingcommenttot;

@Repository
public interface TrainingcommenttotRepository extends CrudRepository<Trainingcommenttot, Integer> {
	@Query(value = "SELECT * FROM trainingcommenttot", nativeQuery = true)
	Page<Trainingcommenttot> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM trainingcommenttot where id =?1", nativeQuery = true)
	Trainingcommenttot findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM trainingcommenttot where status =1", nativeQuery = true)
	List<Trainingcommenttot> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM trainingcommenttot n where n.ten like ?1%", nativeQuery = true)
	List<Trainingcommenttot> findAllByTenDanhMuc(String tenDanhMuc);
	
	@Query(value = "SELECT * FROM trainingcommenttot", nativeQuery = true)
	List<Trainingcommenttot> findAll();
}
