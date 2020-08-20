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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "comment")
public class Comment implements Serializable {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	    @Size(max = 100)
	    @Column(name = "tennguoidung")
	    private String tennguoidung;
	    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "email")
	    private String email;
	    @Lob
	    @Size(max = 65535)
	    @Column(name = "noidung")
	    private String noidung;
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

	    public Comment() {
	    }

	    public Comment(Integer id) {
	        this.id = id;
	    }

	    public Comment(Integer id, String email, Date ngaytao, int status) {
	        this.id = id;
	        this.email = email;
	        this.ngaytao = ngaytao;
	        this.status = status;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
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
	        if (!(object instanceof Comment)) {
	            return false;
	        }
	        Comment other = (Comment) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.dangducton.entities.Comment[ id=" + id + " ]";
	    }
	    
	}
