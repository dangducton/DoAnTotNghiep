package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Thanhtoan;

@Repository
public interface ThanhToanRepository extends CrudRepository<Thanhtoan, Integer> {
	@Query(value = "SELECT * FROM thanhtoan", nativeQuery = true)
	Page<Thanhtoan> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM thanhtoan where id =?1", nativeQuery = true)
	Thanhtoan findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM thanhtoan where status =1", nativeQuery = true)
	List<Thanhtoan> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM thanhtoan n where n.ten like ?1%", nativeQuery = true)
	List<Thanhtoan> findAllByTenDanhMuc(String tenDanhMuc);
}
