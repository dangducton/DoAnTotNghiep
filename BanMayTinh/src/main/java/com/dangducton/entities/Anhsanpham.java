package com.dangducton.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "anhsanpham")
public class Anhsanpham implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "url_anh")
	private String urlAnh;
	@Basic(optional = false)
	@NotNull
	@Column(name = "status")
	private int status;
	@JoinColumn(name = "id_sanpham", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Sanpham idSanpham;

	public Anhsanpham() {
	}

	public Anhsanpham(Integer id) {
		this.id = id;
	}

	public Anhsanpham(Integer id, String urlAnh, int status) {
		this.id = id;
		this.urlAnh = urlAnh;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrlAnh() {
		return urlAnh;
	}

	public void setUrlAnh(String urlAnh) {
		this.urlAnh = urlAnh;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		if (!(object instanceof Anhsanpham)) {
			return false;
		}
		Anhsanpham other = (Anhsanpham) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dangducton.entities.Anhsanpham[ id=" + id + " ]";
	}

}
