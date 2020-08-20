package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Nhacungcap;

@Repository
public interface NhaCungCapRepository extends CrudRepository<Nhacungcap, Integer> {
	@Query(value = "SELECT * FROM nhacungcap", nativeQuery = true)
	Page<Nhacungcap> findAllDanhMuc(Pageable pageable);

	@Query(value = "SELECT * FROM nhacungcap where id =?1", nativeQuery = true)
	Nhacungcap findByIdDanhMuc(Integer id);

	@Query(value = "SELECT * FROM nhacungcap where status =1", nativeQuery = true)
	List<Nhacungcap> findByIdDanhMucActive();

	@Query(value = "SELECT * FROM nhacungcap n where n.ten like ?1%", nativeQuery = true)
	List<Nhacungcap> findAllByTenDanhMuc(String tenDanhMuc);
}