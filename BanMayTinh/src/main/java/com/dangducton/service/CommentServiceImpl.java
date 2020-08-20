package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Comment;
import com.dangducton.repository.CommentRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired(required = true)
	public CommentRepository commentRepository;

	@Override
	public Iterable<Comment> findAll() {
		// TODO Auto-generated method stub
		return commentRepository.findAll();
	}

	@Override
	public Optional<Comment> findById(Integer id) {
		// TODO Auto-generated method stub
		return commentRepository.findById(id);
	}

	@Override
	public void save(Comment danhMuc) {
		// TODO Auto-generated method stub
		commentRepository.save(danhMuc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		commentRepository.deleteById(id);
	}

	@Override
	public Page<Comment> findAllDanhMuc(Integer id,Pageable pageable) {
		// TODO Auto-generated method stub
		return commentRepository.findAllDanhMuc(id,pageable);
	}

	@Override
	public Comment findByIdDanhMuc(Integer id) {
		// TODO Auto-generated method stub
		return commentRepository.findByIdDanhMuc(id);
	}

	@Override
	public List<Comment> findByIdDanhMucActive() {
		// TODO Auto-generated method stub
		return commentRepository.findByIdDanhMucActive();
	}

	@Override
	public List<Comment> findAllByHienThi(Integer id) {
		// TODO Auto-generated method stub
		return commentRepository.findAllByHienThi(id);
	}

	@Override
	public Page<Comment> findAllDanhMuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return commentRepository.findAllDanhMuc(pageable);
	}

	@Override
	public long countcomment() {
		// TODO Auto-generated method stub
		return commentRepository.countcomment();
	}

	@Override
	public List<Comment> findByIdCMT() {
		// TODO Auto-generated method stub
		return commentRepository.findByIdCMT();
	}
}
