package com.dangducton.dto;

public class NhapSanPhamGroupBy {
	private int IdSanPham;
	private int sumSoLuong;

	public int getIdSanPham() {
		return IdSanPham;
	}

	public void setIdSanPham(int idSanPham) {
		IdSanPham = idSanPham;
	}

	public int getSumSoLuong() {
		return sumSoLuong;
	}

	public void setSumSoLuong(int sumSoLuong) {
		this.sumSoLuong = sumSoLuong;
	}

	public NhapSanPhamGroupBy() {

	}

	public NhapSanPhamGroupBy(int idSanPham, int sumSoLuong) {
		super();
		IdSanPham = idSanPham;
		this.sumSoLuong = sumSoLuong;
	}

}
