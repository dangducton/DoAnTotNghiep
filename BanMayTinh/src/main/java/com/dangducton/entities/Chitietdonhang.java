package com.dangducton.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "chitietdonhang")
public class Chitietdonhang implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "giasanpham")
	private double giasanpham;
	@Basic(optional = false)
	@NotNull
	@Column(name = "gianhapsanpham")
	private double gianhapsanpham;

	public double getGianhapsanpham() {
		return gianhapsanpham;
	}

	public void setGianhapsanpham(double gianhapsanpham) {
		this.gianhapsanpham = gianhapsanpham;
	}

	@Basic(optional = false)
	@NotNull
	@Column(name = "soluong")
	private int soluong;
	@Basic(optional = false)
	@NotNull
	@Column(name = "giotao")
	@Temporal(TemporalType.TIME)
	private Date giotao;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ngaytao")
	@Temporal(TemporalType.DATE)
	private Date ngaytao;
	@Basic(optional = false)
	@NotNull
	@Column(name = "status")
	private int status;
	@JoinColumn(name = "id_donhang", referencedColumnName = "id")
	@ManyToOne
	private Donhang idDonhang;
	@JoinColumn(name = "id_nguoidung", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Nguoidung idNguoidung;
	@JoinColumn(name = "id_sanpham", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Sanpham idSanpham;

	public Chitietdonhang() {
	}

	public Chitietdonhang(Integer id) {
		this.id = id;
	}

	public Chitietdonhang(Integer id, double giasanpham, int soluong, Date giotao, Date ngaytao, int status,
			double gianhapsanpham) {
		this.id = id;
		this.giasanpham = giasanpham;
		this.gianhapsanpham = gianhapsanpham;
		this.soluong = soluong;
		this.giotao = giotao;
		this.ngaytao = ngaytao;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getGiotao() {
		return giotao;
	}

	public void setGiotao(Date giotao) {
		this.giotao = giotao;
	}

	public Date getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(Date ngaytao) {
		this.ngaytao = ngaytao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Donhang getIdDonhang() {
		return idDonhang;
	}

	public void setIdDonhang(Donhang idDonhang) {
		this.idDonhang = idDonhang;
	}

	public Nguoidung getIdNguoidung() {
		return idNguoidung;
	}

	public void setIdNguoidung(Nguoidung idNguoidung) {
		this.idNguoidung = idNguoidung;
	}

	public Sanpham getIdSanpham() {
		return idSanpham;
	}

	public void setIdSanpham(Sanpham idSanpham) {
		this.idSanpham = idSanpham;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Chitietdonhang)) {
			return false;
		}
		Chitietdonhang other = (Chitietdonhang) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dangducton.entities.Chitietdonhang[ id=" + id + " ]";
	}

}
