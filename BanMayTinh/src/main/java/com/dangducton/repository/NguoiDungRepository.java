package com.dangducton.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Nguoidung;

@Repository
public interface NguoiDungRepository extends CrudRepository<Nguoidung, Integer> {
	@Query(value = "SELECT * FROM nguoidung n where n.email = ?1 and status = 1", nativeQuery = true)
	Nguoidung findByEmailAndEnabled(String email);

	@Query(value = "SELECT * FROM nguoidung n where n.email = ?1", nativeQuery = true)
	Nguoidung checkEmail(String email);

	Nguoidung findByEmail(String email);

	@Query(value = "SELECT *from nguoidung c JOIN user_role n ON c.id = n.id_nguoidung where n.id_role = 4", nativeQuery = true)
	Page<Nguoidung> findAllNguoiDung(Pageable pageable);

	@Query(value = "SELECT * FROM nguoidung c JOIN user_role n ON c.id = n.id_nguoidung where n.id_role NOT IN (4)", nativeQuery = true)
	Page<Nguoidung> findAllQuanTri(Pageable pageable);

	@Query(value = "SELECT count(id) from nguoidung c JOIN user_role n ON c.id = n.id_nguoidung where n.id_role NOT IN (4)", nativeQuery = true)
	public long countQuanTri();

	@Query(value = "SELECT count(id) from nguoidung c JOIN user_role n ON c.id = n.id_nguoidung where n.id_role = 4", nativeQuery = true)
	public long countNguoiDung();
}
