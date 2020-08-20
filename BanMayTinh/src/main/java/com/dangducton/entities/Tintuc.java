package com.dangducton.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name = "tintuc")
public class Tintuc implements Serializable {

    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	    @Basic(optional = false)
	    @NotNull
	    @Lob
	    @Size(min = 1, max = 65535)
	    @Column(name = "motangan")
	    private String motangan;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 255)
	    @Column(name = "tentintuc")
	    private String tentintuc;
	    @Basic(optional = false)
	    @NotNull
	    @Lob
	    @Size(min = 1, max = 65535)
	    @Column(name = "noidung")
	    private String noidung;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 255)
	    @Column(name = "hinhanh")
	    private String hinhanh;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "ngaytao")
	    @Temporal(TemporalType.DATE)
	    private Date ngaytao;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "status")
	    private int status;

	    public Tintuc() {
	    }

	    public Tintuc(Integer id) {
	        this.id = id;
	    }

	    public Tintuc(Integer id, String motangan, String tentintuc, String noidung, String hinhanh, Date ngaytao, int status) {
	        this.id = id;
	        this.motangan = motangan;
	        this.tentintuc = tentintuc;
	        this.noidung = noidung;
	        this.hinhanh = hinhanh;
	        this.ngaytao = ngaytao;
	        this.status = status;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getMotangan() {
	        return motangan;
	    }

	    public void setMotangan(String motangan) {
	        this.motangan = motangan;
	    }

	    public String getTentintuc() {
	        return tentintuc;
	    }

	    public void setTentintuc(String tentintuc) {
	        this.tentintuc = tentintuc;
	    }

	    public String getNoidung() {
	        return noidung;
	    }

	    public void setNoidung(String noidung) {
	        this.noidung = noidung;
	    }

	    public String getHinhanh() {
	        return hinhanh;
	    }

	    public void setHinhanh(String hinhanh) {
	        this.hinhanh = hinhanh;
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

	    @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (id != null ? id.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Tintuc)) {
	            return false;
	        }
	        Tintuc other = (Tintuc) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.hoanganhduc.entities.Tintuc[ id=" + id + " ]";
	    }
	    
	}
