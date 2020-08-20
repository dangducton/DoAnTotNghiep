package com.dangducton.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	@Query(value = "SELECT * FROM ConfirmationToken where confirmation_token= ?1", nativeQuery = true)
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}