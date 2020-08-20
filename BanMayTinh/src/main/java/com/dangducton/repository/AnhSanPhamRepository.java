package com.dangducton.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Anhsanpham;
@Repository
public interface AnhSanPhamRepository extends CrudRepository<Anhsanpham, Integer>{
	@Query(value = "SELECT * FROM anhsanpham GROUP BY id_sanpham", nativeQuery = true)
	Page<Anhsanpham> findAllAnhSanPham(Pageable pageable);
	
	@Query(value = "SELECT * FROM anhsanpham where id =?1", nativeQuery = true)
	Anhsanpham findByIdAnhSanPham(Integer id);
	
	@Query(value = "SELECT * FROM anhsanpham where status = 1", nativeQuery = true)
	List<Anhsanpham> findByIdAnhSanPhamActive();
	
	@Query(value = "SELECT * FROM anhsanpham where status =1 GROUP BY id_sanpham", nativeQuery = true)
	List<Anhsanpham> findByIdAnhSanPhamActive1();

	@Query(value = "SELECT * FROM anhsanpham where id_sanpham = ?1 GROUP BY id_sanpham", nativeQuery = true)
	Anhsanpham findSanPhamByChiTietDonHang(Integer id);
	
	@Query(value = "SELECT * FROM anhsanpham where id_sanpham = ?1", nativeQuery = true)
	List<Anhsanpham> findByIdAnhSanPhamIdSanPham(Integer id);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_danhmuc = ?1 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllSanPhamTheoDanhMuc(Integer id,Pageable pageable);
	
	
	@Query(
			  value = "select *from anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_danhmuc = ?1 and n.id_nhacungcap = ?2 and n.giaban BETWEEN ?3 AND ?4 group by n.id", 
			  nativeQuery = true)
	Page<Anhsanpham> timKiemTheoDanhMucNhaCungCapGiaSanPham(Integer idDanhMuc,Integer idNhaCungCap,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
	
	@Query(
			  value = "select *from anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_danhmuc = ?1 and n.id_nhacungcap = ?2 group by n.id", 
			  nativeQuery = true)
	Page<Anhsanpham> timKiemTheoDanhMucNhaCungCap(Integer idDanhMuc,Integer idNhaCungCap,Pageable pageable);
	
	@Query(
			  value = "select *from anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_danhmuc = ?1 and n.giaban BETWEEN ?2 AND ?3 group by n.id", 
			  nativeQuery = true)
	Page<Anhsanpham> timKiemTheoDanhMucGiaSanPham(Integer idDanhMuc,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
	
	@Query(
			  value = "select *from anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_nhacungcap = ?1 and n.giaban BETWEEN ?2 AND ?3 group by n.id", 
			  nativeQuery = true)
	Page<Anhsanpham> timKiemTheoNhaCungCapGiaSanPham(Integer idNhaCungCap,Integer giaBatDau,Integer giaKetThuc,Pageable pageable);
	
	
	
	
	
	
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.ten like %?1% or n.giaban like %?1% or n.id_chitiet like %?1% GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllByTenSanPham(String tenSanPham,Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.status = 1 and n.ten like %?1% or n.giaban like %?1% or n.id_chitiet like %?1% GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllByTenSanPhamActive(String tenSanPham,Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.status = 1 and n.giaban BETWEEN ?1 AND ?2 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllByGia(int giabdatdau,int giaketthuc,Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_danhmuc = ?1 and n.status =1 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllSanPhamTheoDanhMucActive(Integer id,Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.status = 1 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllBySanPhamActive(Pageable pageable);
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN nhapsanpham n ON c.id_sanpham = n.id_sanpham where n.status = 1 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllBySanPhamTheoNhap(Pageable pageable);
	
	@Query(value = "SELECT * FROM anhsanpham where status = 1 group by id_sanpham ORDER BY RAND() LIMIT 4", nativeQuery = true)
	List<Anhsanpham> findAllByAnhSanPhamTheoSanPham();
	
	@Query(value = "SELECT * FROM anhsanpham where id_sanpham = ?1 and status = 1 group by id_sanpham", nativeQuery = true)
	Anhsanpham findAllByAnhSanPhamTheoSanPham(Integer id);
	
	@Query(value = "SELECT * FROM anhsanpham c JOIN chitietdonhang n ON c.id_sanpham = n.id_sanpham where n.status = 1 GROUP BY n.id_sanpham order by sum(n.soluong) desc LIMIT 6", nativeQuery = true)
	List<Anhsanpham> findAllSanPhamMuaNhieuNhat();
	
	@Query(value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id GROUP BY c.id_sanpham order by c.id desc LIMIT 6", nativeQuery = true)
	List<Anhsanpham> findAllSanPhamMuaMoiNhat();
	
	@Query(value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id GROUP BY c.id_sanpham order by (n.giaban - n.giamgia) desc LIMIT 3", nativeQuery = true)
	List<Anhsanpham> findAllSanPhamSellNhieuNhap();
	
	@Query(
			  value = "SELECT * FROM anhsanpham c JOIN sanpham n ON c.id_sanpham = n.id where n.id_nhacungcap = ?1 and n.status =1 GROUP BY c.id_sanpham", 
			  nativeQuery = true)
	Page<Anhsanpham> findAllSanPhamTheoNhaCungCapActive(Integer id,Pageable pageable);
}
