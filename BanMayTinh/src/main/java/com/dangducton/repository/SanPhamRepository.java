package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Sanpham;

@Repository
public interface SanPhamRepository extends CrudRepository<Sanpham, Integer>{
	@Query(value = "SELECT * FROM sanpham", nativeQuery = true)
	Page<Sanpham> findAllSanPham(Pageable pageable);
	
	@Query(value = "SELECT * FROM sanpham where id =?1", nativeQuery = true)
	Sanpham findByIdSanPham(Integer id);
	
	@Query(value = "SELECT * FROM sanpham where status =1", nativeQuery = true)
	List<Sanpham> findByIdSanPhamActive();
	
	@Query(value = "SELECT * FROM sanpham n where n.ten like ?1%", nativeQuery = true)
	List<Sanpham> findAllByTenSanPham(String tenDanhMuc);
	
	@Query(value ="SELECT count(id) from sanpham", nativeQuery = true)
	public long count();
	
}

