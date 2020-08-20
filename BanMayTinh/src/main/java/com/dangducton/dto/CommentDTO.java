package com.dangducton.dto;

import java.util.Date;

public class CommentDTO {
	private Integer idcomment;
	private String tensanpham;
	private String anhsanpham;
	private String tennguoidung;
	private String email;
	private String noidung;
	private double xacsuatkhen;
	private double xacsuatche;
	private String danhgia;
	private Date ngaytao;
	private int status;
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getIdcomment() {
		return idcomment;
	}

	public void setIdcomment(Integer idcomment) {
		this.idcomment = idcomment;
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

	public String getTennguoidung() {
		return tennguoidung;
	}

	public void setTennguoidung(String tennguoidung) {
		this.tennguoidung = tennguoidung;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public double getXacsuatkhen() {
		return xacsuatkhen;
	}

	public void setXacsuatkhen(double xacsuatkhen) {
		this.xacsuatkhen = xacsuatkhen;
	}

	public double getXacsuatche() {
		return xacsuatche;
	}

	public void setXacsuatche(double xacsuatche) {
		this.xacsuatche = xacsuatche;
	}

	public String getDanhgia() {
		return danhgia;
	}

	public void setDanhgia(String danhgia) {
		this.danhgia = danhgia;
	}

	public Date getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

	public CommentDTO() {
	}

	public CommentDTO(Integer idcomment, String tensanpham, String anhsanpham, String tennguoidung, String email,
			String noidung, double xacsuatkhen, double xacsuatche, String danhgia, Date ngaytao,int status) {
		super();
		this.idcomment = idcomment;
		this.tensanpham = tensanpham;
		this.anhsanpham = anhsanpham;
		this.tennguoidung = tennguoidung;
		this.email = email;
		this.noidung = noidung;
		this.xacsuatkhen = xacsuatkhen;
		this.xacsuatche = xacsuatche;
		this.danhgia = danhgia;
		this.ngaytao = ngaytao;
		this.status = status;
	}

}
