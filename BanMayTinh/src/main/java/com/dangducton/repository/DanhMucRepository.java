package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Danhmuc;


@Repository
public interface DanhMucRepository extends CrudRepository<Danhmuc, Integer> {
	@Query(value = "SELECT * FROM danhmuc", nativeQuery = true)
	Page<Danhmuc> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM danhmuc where id =?1", nativeQuery = true)
	Danhmuc findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM danhmuc where status =1", nativeQuery = true)
	List<Danhmuc> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM danhmuc n where n.ten like ?1%", nativeQuery = true)
	List<Danhmuc> findAllByTenDanhMuc(String tenDanhMuc);
}
