package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Trainingcommentxau;

@Repository
public interface TrainingcommentxauRepository extends CrudRepository<Trainingcommentxau, Integer> {
	@Query(value = "SELECT * FROM trainingcommentxau", nativeQuery = true)
	Page<Trainingcommentxau> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM trainingcommentxau where id =?1", nativeQuery = true)
	Trainingcommentxau findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM trainingcommentxau where status =1", nativeQuery = true)
	List<Trainingcommentxau> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM trainingcommentxau n where n.ten like ?1%", nativeQuery = true)
	List<Trainingcommentxau> findAllByTenDanhMuc(String tenDanhMuc);
	
	@Query(value = "SELECT * FROM trainingcommentxau", nativeQuery = true)
	List<Trainingcommentxau> findAll();
}
