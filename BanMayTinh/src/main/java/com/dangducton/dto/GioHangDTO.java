package com.dangducton.dto;

public class GioHangDTO {
	private Integer id;
	private Integer idChiTietDonHang;
	private Integer idSanPham;
	private String idChiTietSanPham;
	private String ten;
	private String anh;
	private double giasanpham;
	private int soluong;

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

	public Integer getIdChiTietDonHang() {
		return idChiTietDonHang;
	}

	public void setIdChiTietDonHang(Integer idChiTietDonHang) {
		this.idChiTietDonHang = idChiTietDonHang;
	}

	public String getTen() {
		return ten;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public double getGiasanpham() {
		return giasanpham;
	}

	public void setGiasanpham(double giasanpham) {
		this.giasanpham = giasanpham;
	}

	public int getSoluong() {
		return soluong;
	}

	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

	public GioHangDTO() {
		super();
	}

	public GioHangDTO(String ten, String anh, double giasanpham, int soluong, Integer id, Integer idSanPham,
			Integer idChiTietDonHang, String idChiTietSanPham) {
		super();
		this.idChiTietSanPham = idChiTietSanPham;
		this.idChiTietDonHang = idChiTietDonHang;
		this.idSanPham = idSanPham;
		this.id = id;
		this.ten = ten;
		this.anh = anh;
		this.giasanpham = giasanpham;
		this.soluong = soluong;
	}

}
