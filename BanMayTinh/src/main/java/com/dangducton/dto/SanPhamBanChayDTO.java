package com.dangducton.dto;

public class SanPhamBanChayDTO {
	private Integer idSanPham;
	private String idChiTietSanPham;
	private String anhSanPham;
	private int soLuong;
	private String tenSanPham;
	private double giaNhap;
	private double giaBan;
	
	public String getIdChiTietSanPham() {
		return idChiTietSanPham;
	}
	public void setIdChiTietSanPham(String idChiTietSanPham) {
		this.idChiTietSanPham = idChiTietSanPham;
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
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public double getGiaNhap() {
		return giaNhap;
	}
	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}
	public double getGiaBan() {
		return giaBan;
	}
	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}
	public SanPhamBanChayDTO(Integer idSanPham, String anhSanPham, int soLuong, String tenSanPham, double giaNhap,
			double giaBan,String idChiTietSanPham) {
		super();
		this.idChiTietSanPham = idChiTietSanPham;
		this.idSanPham = idSanPham;
		this.anhSanPham = anhSanPham;
		this.soLuong = soLuong;
		this.tenSanPham = tenSanPham;
		this.giaNhap = giaNhap;
		this.giaBan = giaBan;
	}
	
}
