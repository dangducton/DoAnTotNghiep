package com.dangducton.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Role;

@Repository
public interface RoleRespository extends CrudRepository<Role, Long> {
	@Query(value = "SELECT * FROM role n where n.ten = ?1", nativeQuery = true)
	Role findByName(String name);
}