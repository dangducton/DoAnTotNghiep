package com.dangducton.dto;

public class SanPhamYeuThichDTO {
	private String tenSanPham;
	private Integer idSanPham;
	private String anhSanPham;
	private double giaBan;
	private double giamGia;
	private Integer idSanPhamYeuThich;

	public String getTenSanPham() {
		return tenSanPham;
	}

	public Integer getIdSanPhamYeuThich() {
		return idSanPhamYeuThich;
	}

	public void setIdSanPhamYeuThich(Integer idSanPhamYeuThich) {
		this.idSanPhamYeuThich = idSanPhamYeuThich;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public Integer getIdSanPham() {
		return idSanPham;
	}

	public void setIdSanPham(Integer idSanPham) {
		this.idSanPham = idSanPham;
	}

	public String getAnhSanPham() {
		return anhSanPham;
	}

	public void setAnhSanPham(String anhSanPham) {
		this.anhSanPham = anhSanPham;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public double getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(double giamGia) {
		this.giamGia = giamGia;
	}

	public SanPhamYeuThichDTO() {

	}

	public SanPhamYeuThichDTO(String tenSanPham, Integer idSanPham, String anhSanPham, double giaBan, double giamGia,Integer idSanPhamYeuThich) {
		super();
		this.idSanPhamYeuThich = idSanPhamYeuThich;
		this.tenSanPham = tenSanPham;
		this.idSanPham = idSanPham;
		this.anhSanPham = anhSanPham;
		this.giaBan = giaBan;
		this.giamGia = giamGia;
	}

}