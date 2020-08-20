package com.dangducton.dto;

public class TonKhoDTO {
	private Integer idsanpham;
	private String tensanpham;
	private String anhsanpham;
	private double tongnhap;
	private double tongban;
	private double tonkho;

	public Integer getIdsanpham() {
		return idsanpham;
	}

	public void setIdsanpham(Integer idsanpham) {
		this.idsanpham = idsanpham;
	}

	public String getTensanpham() {
		return tensanpham;
	}

	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}

	public String getAnhsanpham() {
		return anhsanpham;
	}

	public void setAnhsanpham(String anhsanpham) {
		this.anhsanpham = anhsanpham;
	}

	public double getTongnhap() {
		return tongnhap;
	}

	public void setTongnhap(double tongnhap) {
		this.tongnhap = tongnhap;
	}

	public double getTongban() {
		return tongban;
	}

	public void setTongban(double tongban) {
		this.tongban = tongban;
	}

	public double getTonkho() {
		return tonkho;
	}

	public void setTonkho(double tonkho) {
		this.tonkho = tonkho;
	}

	public TonKhoDTO() {
		super();
	}

	public TonKhoDTO(String tensanpham, String anhsanpham, double tongnhap, double tongban, double tonkho,Integer idsanpham) {
		super();
		this.idsanpham = idsanpham;
		this.tensanpham = tensanpham;
		this.anhsanpham = anhsanpham;
		this.tongnhap = tongnhap;
		this.tongban = tongban;
		this.tonkho = tonkho;
	}

}
