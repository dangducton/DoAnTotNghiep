package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangducton.entities.Comment;

public interface CommentService {
	Iterable<Comment> findAll();

	Optional<Comment> findById(Integer id);

	void save(Comment danhMuc);

	void delete(Integer id);
	
	Page<Comment> findAllDanhMuc(Integer id,Pageable pageable);
	
	Comment findByIdDanhMuc(Integer id);
	
	List<Comment> findByIdDanhMucActive();
	
	List<Comment> findAllByHienThi(Integer id);
	
	Page<Comment> findAllDanhMuc(Pageable pageable);
	
	public long countcomment();
	
	List<Comment> findByIdCMT();
}
