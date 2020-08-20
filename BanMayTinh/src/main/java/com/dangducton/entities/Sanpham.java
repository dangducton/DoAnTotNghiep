package com.dangducton.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sanpham")
public class Sanpham implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "ten")
	private String ten;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "motangan")
	private String motangan;
	@Basic(optional = false)
	@NotNull
	@Lob
	@Size(min = 1, max = 65535)
	@Column(name = "mota")
	private String mota;
	@Basic(optional = false)
	@NotNull
	@Column(name = "gianhap")
	private double gianhap;
	@Basic(optional = false)
	@NotNull
	@Column(name = "giaban")
	private double giaban;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "giamgia")
	private Double giamgia;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ngaytao")
	@Temporal(TemporalType.DATE)
	private Date ngaytao;
	@Basic(optional = false)
	@NotNull
	@Column(name = "status")
	private int status;
	@Column(name = "id_chitiet")
	private String idChitiet;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idSanpham")
	private Collection<Anhsanpham> anhsanphamCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idSanpham")
	private Collection<Sanphamyeuthich> sanphamyeuthichCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idSanpham")
	private Collection<Chitietdonhang> chitietdonhangCollection;
	@JoinColumn(name = "id_danhmuc", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Danhmuc idDanhmuc;
	@JoinColumn(name = "id_nhacungcap", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Nhacungcap idNhacungcap;
	
	public Nhacungcap getIdNhacungcap() {
		return idNhacungcap;
	}

	public void setIdNhacungcap(Nhacungcap idNhacungcap) {
		this.idNhacungcap = idNhacungcap;
	}

	public String getIdChitiet() {
		return idChitiet;
	}

	public void setIdChitiet(String idChitiet) {
		this.idChitiet = idChitiet;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idSanpham")
	private Collection<Comment> commentCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idSanpham")
	private Collection<Nhapsanpham> nhapsanphamCollection;

	public Sanpham() {
	}

	public Sanpham(Integer id) {
		this.id = id;
	}

	public Sanpham(Integer id, String ten, String motangan, String mota, double gianhap, double giaban, Date ngaytao,String idChitiet,
			int status) {
		this.id = id;
		this.ten = ten;
		this.motangan = motangan;
		this.idChitiet = idChitiet;
		this.mota = mota;
		this.gianhap = gianhap;
		this.giaban = giaban;
		this.ngaytao = ngaytao;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMotangan() {
		return motangan;
	}

	public void setMotangan(String motangan) {
		this.motangan = motangan;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public double getGianhap() {
		return gianhap;
	}

	public void setGianhap(double gianhap) {
		this.gianhap = gianhap;
	}

	public double getGiaban() {
		return giaban;
	}

	public void setGiaban(double giaban) {
		this.giaban = giaban;
	}

	public Double getGiamgia() {
		return giamgia;
	}

	public void setGiamgia(Double giamgia) {
		this.giamgia = giamgia;
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

	public Collection<Anhsanpham> getAnhsanphamCollection() {
		return anhsanphamCollection;
	}

	public void setAnhsanphamCollection(Collection<Anhsanpham> anhsanphamCollection) {
		this.anhsanphamCollection = anhsanphamCollection;
	}

	public Collection<Sanphamyeuthich> getSanphamyeuthichCollection() {
		return sanphamyeuthichCollection;
	}

	public void setSanphamyeuthichCollection(Collection<Sanphamyeuthich> sanphamyeuthichCollection) {
		this.sanphamyeuthichCollection = sanphamyeuthichCollection;
	}

	public Collection<Chitietdonhang> getChitietdonhangCollection() {
		return chitietdonhangCollection;
	}

	public void setChitietdonhangCollection(Collection<Chitietdonhang> chitietdonhangCollection) {
		this.chitietdonhangCollection = chitietdonhangCollection;
	}

	public Danhmuc getIdDanhmuc() {
		return idDanhmuc;
	}

	public void setIdDanhmuc(Danhmuc idDanhmuc) {
		this.idDanhmuc = idDanhmuc;
	}

	public Collection<Comment> getCommentCollection() {
		return commentCollection;
	}

	public void setCommentCollection(Collection<Comment> commentCollection) {
		this.commentCollection = commentCollection;
	}

	public Collection<Nhapsanpham> getNhapsanphamCollection() {
		return nhapsanphamCollection;
	}

	public void setNhapsanphamCollection(Collection<Nhapsanpham> nhapsanphamCollection) {
		this.nhapsanphamCollection = nhapsanphamCollection;
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
		if (!(object instanceof Sanpham)) {
			return false;
		}
		Sanpham other = (Sanpham) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dangducton.entities.Sanpham[ id=" + id + " ]";
	}

}
