package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Comment;
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
	@Query(value = "SELECT * FROM comment n where n.id_sanpham = ?1 and status = 1 order by id desc", nativeQuery = true)
	Page<Comment> findAllDanhMuc(Integer id,Pageable pageable);
	
	@Query(value = "SELECT * FROM comment order by id desc", nativeQuery = true)
	Page<Comment> findAllDanhMuc(Pageable pageable);
	
	@Query(value = "SELECT * FROM comment where id =?1", nativeQuery = true)
	Comment findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM comment where status =1", nativeQuery = true)
	List<Comment> findByIdDanhMucActive();
	
	@Query(value = "SELECT * FROM comment order by id desc", nativeQuery = true)
	List<Comment> findByIdCMT();
	
	@Query(value = "SELECT * FROM comment n where n.id_sanpham = ?1", nativeQuery = true)
	List<Comment> findAllByHienThi(Integer id);
	
	@Query(value ="SELECT count(id) from comment", nativeQuery = true)
	public long countcomment();
}
