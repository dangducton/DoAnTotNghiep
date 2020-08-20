package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Vanchuyen;

@Repository
public interface VanChuyenRepository extends CrudRepository<Vanchuyen, Integer> {
	@Query(value = "SELECT * FROM vanchuyen", nativeQuery = true)
	Page<Vanchuyen> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM vanchuyen where id =?1", nativeQuery = true)
	Vanchuyen findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM vanchuyen where status =1", nativeQuery = true)
	List<Vanchuyen> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM vanchuyen n where n.ten like ?1%", nativeQuery = true)
	List<Vanchuyen> findAllByTenDanhMuc(String tenDanhMuc);
}
