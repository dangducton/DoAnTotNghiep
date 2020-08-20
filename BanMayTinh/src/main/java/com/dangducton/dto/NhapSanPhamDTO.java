package com.dangducton.dto;

import java.util.Date;

public class NhapSanPhamDTO {
	Integer id;
	Integer idSanPham;
	String tensanpham;
	String IdChiTietSanPham;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	double gianhap;
	int soluong;
	Date ngaytao;
	String anhsanpham;

	public Integer getIdSanPham() {
		return idSanPham;
	}

	public void setIdSanPham(Integer idSanPham) {
		this.idSanPham = idSanPham;
	}

	public String getTensanpham() {
		return tensanpham;
	}

	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}

	public String getIdChiTietSanPham() {
		return IdChiTietSanPham;
	}

	public void setIdChiTietSanPham(String idChiTietSanPham) {
		IdChiTietSanPham = idChiTietSanPham;
	}

	public double getGianhap() {
		return gianhap;
	}

	public void setGianhap(double gianhap) {
		this.gianhap = gianhap;
	}

	public int getSoluong() {
		return soluong;
	}

	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

	public Date getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getAnhsanpham() {
		return anhsanpham;
	}

	public void setAnhsanpham(String anhsanpham) {
		this.anhsanpham = anhsanpham;
	}

	public NhapSanPhamDTO(Integer id, Integer idSanPham, String tensanpham, String idChiTietSanPham, double gianhap,
			int soluong, Date ngaytao, String anhsanpham) {
		super();
		this.id = id;
		this.idSanPham = idSanPham;
		this.tensanpham = tensanpham;
		IdChiTietSanPham = idChiTietSanPham;
		this.gianhap = gianhap;
		this.soluong = soluong;
		this.ngaytao = ngaytao;
		this.anhsanpham = anhsanpham;
	}

}
