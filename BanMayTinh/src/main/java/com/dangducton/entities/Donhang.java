package com.dangducton.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "donhang")
public class Donhang implements Serializable {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "id_chitietdonhang")
	    private String idChitietdonhang;
	    @Column(name = "ghichu")
	    private String ghichu;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "tongsotien")
	    private double tongsotien;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "tennguoinhan")
	    private String tennguoinhan;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "diachi")
	    private String diachi;
	    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 100)
	    @Column(name = "email")
	    private String email;
	    @Basic(optional = false)
	    @NotNull
	    @Size(min = 1, max = 30)
	    @Column(name = "dienthoai")
	    private String dienthoai;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "trangthaithanhtoan")
	    private boolean trangthaithanhtoan;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "ngaytao")
	    @Temporal(TemporalType.DATE)
	    private Date ngaytao;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "trangthaidonhang")
	    private int trangthaidonhang;
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "status")
	    private int status;
	    @JoinColumn(name = "id_nguoiduyet", referencedColumnName = "id")
	    @ManyToOne
	    private Nguoidung idNguoiduyet;
	    @JoinColumn(name = "id_nguoidung", referencedColumnName = "id")
	    @ManyToOne(optional = false)
	    private Nguoidung idNguoidung;
	    @JoinColumn(name = "thanhtoan", referencedColumnName = "id")
	    @ManyToOne(optional = false)
	    private Thanhtoan thanhtoan;
	    @JoinColumn(name = "vanchuyen", referencedColumnName = "id")
	    @ManyToOne(optional = false)
	    private Vanchuyen vanchuyen;
	    @OneToMany(mappedBy = "idDonhang")
	    private Collection<Chitietdonhang> chitietdonhangCollection;

	    public Donhang() {
	    }

	    public Donhang(Integer id) {
	        this.id = id;
	    }

	    public Donhang(Integer id, String idChitietdonhang, String ghichu, double tongsotien, String tennguoinhan, String diachi, String email, String dienthoai, boolean trangthaithanhtoan, Date ngaytao, int trangthaidonhang, int status) {
	        this.id = id;
	        this.idChitietdonhang = idChitietdonhang;
	        this.ghichu = ghichu;
	        this.tongsotien = tongsotien;
	        this.tennguoinhan = tennguoinhan;
	        this.diachi = diachi;
	        this.email = email;
	        this.dienthoai = dienthoai;
	        this.trangthaithanhtoan = trangthaithanhtoan;
	        this.ngaytao = ngaytao;
	        this.trangthaidonhang = trangthaidonhang;
	        this.status = status;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getIdChitietdonhang() {
	        return idChitietdonhang;
	    }

	    public void setIdChitietdonhang(String idChitietdonhang) {
	        this.idChitietdonhang = idChitietdonhang;
	    }

	    public String getGhichu() {
	        return ghichu;
	    }

	    public void setGhichu(String ghichu) {
	        this.ghichu = ghichu;
	    }

	    public double getTongsotien() {
	        return tongsotien;
	    }

	    public void setTongsotien(double tongsotien) {
	        this.tongsotien = tongsotien;
	    }

	    public String getTennguoinhan() {
	        return tennguoinhan;
	    }

	    public void setTennguoinhan(String tennguoinhan) {
	        this.tennguoinhan = tennguoinhan;
	    }

	    public String getDiachi() {
	        return diachi;
	    }

	    public void setDiachi(String diachi) {
	        this.diachi = diachi;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getDienthoai() {
	        return dienthoai;
	    }

	    public void setDienthoai(String dienthoai) {
	        this.dienthoai = dienthoai;
	    }

	    public boolean getTrangthaithanhtoan() {
	        return trangthaithanhtoan;
	    }

	    public void setTrangthaithanhtoan(boolean trangthaithanhtoan) {
	        this.trangthaithanhtoan = trangthaithanhtoan;
	    }

	    public Date getNgaytao() {
	        return ngaytao;
	    }

	    public void setNgaytao(Date ngaytao) {
	        this.ngaytao = ngaytao;
	    }

	    public int getTrangthaidonhang() {
	        return trangthaidonhang;
	    }

	    public void setTrangthaidonhang(int trangthaidonhang) {
	        this.trangthaidonhang = trangthaidonhang;
	    }

	    public int getStatus() {
	        return status;
	    }

	    public void setStatus(int status) {
	        this.status = status;
	    }

	    public Nguoidung getIdNguoiduyet() {
	        return idNguoiduyet;
	    }

	    public void setIdNguoiduyet(Nguoidung idNguoiduyet) {
	        this.idNguoiduyet = idNguoiduyet;
	    }

	    public Nguoidung getIdNguoidung() {
	        return idNguoidung;
	    }

	    public void setIdNguoidung(Nguoidung idNguoidung) {
	        this.idNguoidung = idNguoidung;
	    }

	    public Thanhtoan getThanhtoan() {
	        return thanhtoan;
	    }

	    public void setThanhtoan(Thanhtoan thanhtoan) {
	        this.thanhtoan = thanhtoan;
	    }

	    public Vanchuyen getVanchuyen() {
	        return vanchuyen;
	    }

	    public void setVanchuyen(Vanchuyen vanchuyen) {
	        this.vanchuyen = vanchuyen;
	    }

	    public Collection<Chitietdonhang> getChitietdonhangCollection() {
	        return chitietdonhangCollection;
	    }

	    public void setChitietdonhangCollection(Collection<Chitietdonhang> chitietdonhangCollection) {
	        this.chitietdonhangCollection = chitietdonhangCollection;
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
	        if (!(object instanceof Donhang)) {
	            return false;
	        }
	        Donhang other = (Donhang) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.dangducton.entities.Donhang[ id=" + id + " ]";
	    }
	    
	}
