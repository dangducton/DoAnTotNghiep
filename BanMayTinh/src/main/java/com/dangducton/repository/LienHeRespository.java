package com.dangducton.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Lienhe;

@Repository
public interface LienHeRespository extends CrudRepository<Lienhe, Integer>{
	@Query(value = "SELECT * FROM lienhe ORDER BY id DESC", nativeQuery = true)
	Page<Lienhe> findAllLienHe(Pageable pageable);

	@Query(value = "SELECT * FROM lienhe where id =?1", nativeQuery = true)
	Lienhe findByIdLienHe(Integer id);
	
	@Query(value = "SELECT MAX(id) FROM lienhe", nativeQuery = true)
	int findMaxID();
	
	@Query(value ="SELECT count(id) from lienhe", nativeQuery = true)
	public long countLienHe();
}
