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
@Table(name = "nhapsanpham")
public class Nhapsanpham implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "soluong")
    private int soluong;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gianhap")
    private double gianhap;
    public double getGianhap() {
		return gianhap;
	}

	public void setGianhap(double gianhap) {
		this.gianhap = gianhap;
	}

	@Basic(optional = false)
    @NotNull
    @Column(name = "ngaytao")
    @Temporal(TemporalType.DATE)
    private Date ngaytao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "id_sanpham", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sanpham idSanpham;

    public Nhapsanpham() {
    }

    public Nhapsanpham(Integer id) {
        this.id = id;
    }

    public Nhapsanpham(Integer id, int soluong, Date ngaytao, int status,double gianhap) {
        this.id = id;
        this.gianhap = gianhap;
        this.soluong = soluong;
        this.ngaytao = ngaytao;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Nhapsanpham)) {
            return false;
        }
        Nhapsanpham other = (Nhapsanpham) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dangducton.entities.Nhapsanpham[ id=" + id + " ]";
    }
    
}
