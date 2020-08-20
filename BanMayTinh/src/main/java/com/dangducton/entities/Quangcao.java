package com.dangducton.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "quangcao")
public class Quangcao implements Serializable {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 255)
	    @Column(name = "anh")
	    private String anh;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "url_anh")
	    private String urlAnh;
	    @Column(name = "thutusapxep")
	    private Integer thutusapxep;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "status")
	    private int status;

	    public Quangcao() {
	    }

	    public Quangcao(Integer id) {
	        this.id = id;
	    }

	    public Quangcao(Integer id, String anh, String urlAnh, int status) {
	        this.id = id;
	        this.anh = anh;
	        this.urlAnh = urlAnh;
	        this.status = status;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getAnh() {
	        return anh;
	    }

	    public void setAnh(String anh) {
	        this.anh = anh;
	    }

	    public String getUrlAnh() {
	        return urlAnh;
	    }

	    public void setUrlAnh(String urlAnh) {
	        this.urlAnh = urlAnh;
	    }

	    public Integer getThutusapxep() {
	        return thutusapxep;
	    }

	    public void setThutusapxep(Integer thutusapxep) {
	        this.thutusapxep = thutusapxep;
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
	        if (!(object instanceof Quangcao)) {
	            return false;
	        }
	        Quangcao other = (Quangcao) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.dangducton.entities.Quangcao[ id=" + id + " ]";
	    }
	    
	}
