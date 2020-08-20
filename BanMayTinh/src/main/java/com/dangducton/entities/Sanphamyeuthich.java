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
@Table(name = "sanphamyeuthich")
public class Sanphamyeuthich implements Serializable {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "ngaytao")
	    @Temporal(TemporalType.DATE)
	    private Date ngaytao;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "status")
	    private int status;
	    @JoinColumn(name = "id_nguoidung", referencedColumnName = "id")
	    @ManyToOne(optional = false)
	    private Nguoidung idNguoidung;
	    @JoinColumn(name = "id_sanpham", referencedColumnName = "id")
	    @ManyToOne(optional = false)
	    private Sanpham idSanpham;

	    public Sanphamyeuthich() {
	    }

	    public Sanphamyeuthich(Integer id) {
	        this.id = id;
	    }

	    public Sanphamyeuthich(Integer id, Date ngaytao, int status) {
	        this.id = id;
	        this.ngaytao = ngaytao;
	        this.status = status;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
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
	        if (!(object instanceof Sanphamyeuthich)) {
	            return false;
	        }
	        Sanphamyeuthich other = (Sanphamyeuthich) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.dangducton.entities.Sanphamyeuthich[ id=" + id + " ]";
	    }
	    
	}
