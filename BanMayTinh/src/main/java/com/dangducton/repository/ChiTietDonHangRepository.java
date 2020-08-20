package com.dangducton.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dangducton.entities.Chitietdonhang;

@Repository
public interface ChiTietDonHangRepository extends CrudRepository<Chitietdonhang, Integer>{
	@Query(value = "SELECT * FROM chitietdonhang order by id desc", nativeQuery = true)
	Page<Chitietdonhang> findAllChiTietDonHang(Pageable pageable);
		
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and MONTH(n.ngaytao) = ?2 and DAY(n.ngaytao) = ?3 and n.status = 1", nativeQuery = true)
	List<Chitietdonhang> tinhDoanhThuTheoNgay(Integer nam, Integer thang,Integer ngay);
	
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and MONTH(n.ngaytao) = ?2 and n.status = 1", nativeQuery = true)
	List<Chitietdonhang> tinhDoanhThuTheoThang(Integer nam, Integer thang);
	
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and n.status = 1", nativeQuery = true)
	List<Chitietdonhang> tinhDoanhThuTheoNam(Integer nam);
	
	@Query(value = "SELECT * FROM chitietdonhang n where n.status = 1 group by n.id_sanpham order by sum(n.soluong) desc", nativeQuery = true)
	Page<Chitietdonhang> findAllSanPhamBanChay(Pageable pageable);
	
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and MONTH(n.ngaytao) = ?2 and DAY(n.ngaytao) = ?3 and n.status = 1 group by n.id_sanpham order by sum(n.soluong) desc LIMIT 10", nativeQuery = true)
	List<Chitietdonhang> findAllSanPhamBanChayTheoNgay(Integer nam, Integer thang,Integer ngay);
	
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and MONTH(n.ngaytao) = ?2 and n.status = 1 group by n.id_sanpham order by sum(n.soluong) desc LIMIT 10", nativeQuery = true)
	List<Chitietdonhang> findAllSanPhamBanChayTheoThang(Integer nam, Integer thang);
	
	@Query(value = "SELECT * FROM chitietdonhang n where YEAR(n.ngaytao) = ?1 and n.status = 1 group by n.id_sanpham order by sum(n.soluong) desc LIMIT 10", nativeQuery = true)
	List<Chitietdonhang> findAllSanPhamBanChayTheoNam(Integer nam);
	
	@Query(value = "SELECT * FROM chitietdonhang where id =?1", nativeQuery = true)
	Chitietdonhang findByIdChiTietDonHang(Integer id);
	
	@Query(value = "SELECT * FROM chitietdonhang where status =1", nativeQuery = true)
	List<Chitietdonhang> findByIdChiTietDonHangActive();
	
	@Query(value = "SELECT * FROM chitietdonhang n where n.id_nguoidung = ?1 and n.id_donhang is null", nativeQuery = true)
	List<Chitietdonhang> findAllByGioHang(Integer id);
	
	@Query(value = "SELECT * FROM chitietdonhang n where n.id_donhang = ?1", nativeQuery = true)
	List<Chitietdonhang> findAllGioHangTheoDonHang(Integer id);
	
	@Query(value = "SELECT * FROM chitietdonhang n where n.id_nguoidung = ?1 and n.id_sanpham = ?2 and n.id_donhang is null", nativeQuery = true)
	Chitietdonhang findAllByGioHang(Integer id, Integer idSanpham);
	
	@Query(value ="SELECT sum(soluong) from chitietdonhang where id_sanpham = ?1 and status = 1 group by id_sanpham", nativeQuery = true)
	public Integer groupBy(Integer id);
	
	@Query(value ="SELECT count(id_sanpham) from chitietdonhang where id_nguoidung = ?1 and id_donhang is null", nativeQuery = true)
	public Integer count(Integer id);
	
	@Query(value ="SELECT count(id) from chitietdonhang", nativeQuery = true)
	public long countchitietdonhang();
	
	@Query(value ="SELECT sum(soluong) FROM chitietdonhang where status = 1 and id_sanpham = ?1", nativeQuery = true)
	public int tinhSoLuongSanPham(Integer id);
	
	@Query(value ="SELECT sum(soluong) FROM chitietdonhang n where status = 1 and id_sanpham = ?1 and YEAR(n.ngaytao) = ?2 and MONTH(n.ngaytao) = ?3 and DAY(n.ngaytao) = ?4 ", nativeQuery = true)
	public int tinhSoLuongSanPhamTheoNgay(Integer id,Integer nam, Integer thang,Integer ngay);
	
	@Query(value ="SELECT sum(soluong) FROM chitietdonhang n where status = 1 and id_sanpham = ?1 and YEAR(n.ngaytao) = ?2 and MONTH(n.ngaytao) = ?3", nativeQuery = true)
	public int tinhSoLuongSanPhamTheoThang(Integer id,Integer nam, Integer thang);
	
	@Query(value ="SELECT sum(soluong) FROM chitietdonhang n where status = 1 and id_sanpham = ?1 and YEAR(n.ngaytao) = ?2", nativeQuery = true)
	public int tinhSoLuongSanPhamTheoNam(Integer id,Integer nam);
}
