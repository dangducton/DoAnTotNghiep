package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Nhapsanpham;

@Repository
public interface NhapSanPhamRepository extends CrudRepository<Nhapsanpham, Integer> {
	@Query(value ="SELECT sum(soluong) from nhapsanpham where id_sanpham = ?1 group by id_sanpham", nativeQuery = true)
	public Integer groupBy(Integer id);
	
	@Query(
			  value = "SELECT * FROM nhapsanpham WHERE NOT soluong = 0 ORDER BY ngaytao DESC ", 
			  nativeQuery = true)
	Page<Nhapsanpham> findAllByNhapSanPham(Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM nhapsanpham", 
			  nativeQuery = true)
	List<Nhapsanpham> findAllByNhapSanPhamTinhTienNhap();
}

