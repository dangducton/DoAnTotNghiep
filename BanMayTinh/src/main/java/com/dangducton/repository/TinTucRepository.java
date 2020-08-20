package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Tintuc;

@Repository
public interface TinTucRepository extends CrudRepository<Tintuc, Integer> {

	@Query(value = "SELECT * FROM tintuc ORDER BY ngaytao DESC", nativeQuery = true)
	Page<Tintuc> findAllTinTuc(Pageable pageable);

	@Query(value = "SELECT * FROM tintuc where status = 1 ORDER BY ngaytao DESC", nativeQuery = true)
	Page<Tintuc> findAllTinTucActive(Pageable pageable);

	@Query(value = "SELECT * FROM tintuc where id =?1", nativeQuery = true)
	Tintuc findByIdTinTuc(Integer id);

	@Query(value = "SELECT * FROM tintuc where status =1", nativeQuery = true)
	List<Tintuc> findByIdTinTucActive();
	
	@Query(value = "SELECT * FROM tintuc where status = 1 ORDER BY ngaytao DESC LIMIT 3", nativeQuery = true)
	List<Tintuc> findAllTinTucMoiNhat();
	
	@Query(value = "SELECT * FROM tintuc where status = 1 ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Tintuc> findAllTinTucRandom();
	
	@Query(
			  value = "SELECT * FROM tintuc where tentintuc like %?1%", 
			  nativeQuery = true)
	Page<Tintuc> findAllByTenSanPham(String tenSanPham,Pageable pageable);
}
