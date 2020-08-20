package com.dangducton.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Sanphamyeuthich;

@Repository
public interface SanPhamYeuThichRepository extends CrudRepository<Sanphamyeuthich, Integer>{
	@Query(value = "SELECT * FROM sanphamyeuthich where id_nguoidung = ?1", nativeQuery = true)
	List<Sanphamyeuthich> findAllByNguoiDung(Integer id);
	
	@Query(value = "SELECT * FROM sanphamyeuthich n where n.id_nguoidung = ?1 and n.id_sanpham = ?2", nativeQuery = true)
	Sanphamyeuthich findAllBySanPhamYeuThich(Integer id, Integer idSanpham);
	
	@Query(value ="SELECT count(id_sanpham) from sanphamyeuthich where id_nguoidung = ?1", nativeQuery = true)
	public Integer count(Integer id);
	
	@Query(value ="SELECT count(id) from sanphamyeuthich", nativeQuery = true)
	public long countsanphamyeuthich();
}
