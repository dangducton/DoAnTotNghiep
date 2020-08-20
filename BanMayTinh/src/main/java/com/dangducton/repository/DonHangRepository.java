package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Donhang;

@Repository
public interface DonHangRepository extends CrudRepository<Donhang, Integer> {
	@Query(value = "SELECT * FROM donhang ORDER BY id DESC", nativeQuery = true)
	Page<Donhang> findAllDanhMuc(Pageable pageable);

	@Query(value = "SELECT * FROM donhang where id =?1", nativeQuery = true)
	Donhang findByIdDanhMuc(Integer id);
	
	@Query(value = "SELECT * FROM donhang where id_nguoidung =?1", nativeQuery = true)
	Page<Donhang> findByIdDonHangByNguoiDung(Integer id,Pageable pageable);

	@Query(value = "SELECT * FROM donhang where status = 1", nativeQuery = true)
	List<Donhang> findByIdDanhMucActive();
	
	@Query(value = "SELECT MAX(id) FROM donhang", nativeQuery = true)
	int findMaxID();
	
	@Query(value ="SELECT count(id) from donhang", nativeQuery = true)
	public long countdonhang();
	
	@Query(value ="SELECT count(id) from donhang where id_nguoiduyet is null", nativeQuery = true)
	public long countdonhangmoi();
	
	@Query(value = "SELECT * FROM donhang where id_nguoiduyet is null", nativeQuery = true)
	List<Donhang> findByListDonHangMoi();
	
	@Query(value = "SELECT * FROM donhang where trangthaidonhang = ?1", nativeQuery = true)
	Page<Donhang> findDonHangByTrangThaiDonHang(Integer id,Pageable pageable);
	
	@Query(value = "SELECT * FROM donhang c JOIN nguoidung n ON c.id_nguoiduyet = n.id where n.hoten like ?1% or c.id_chitietdonhang like ?1% or c.tongsotien like ?1% or c.diachi like ?1% or c.dienthoai like ?1% or c.email like ?1% or c.tennguoinhan like ?1% GROUP BY c.id", 
			  nativeQuery = true)
	Page<Donhang> findAllByTen(String tenSanPham,Pageable pageable);
}