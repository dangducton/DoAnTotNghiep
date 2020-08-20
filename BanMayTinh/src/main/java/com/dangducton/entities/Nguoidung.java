package com.dangducton.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "nguoidung")
public class Nguoidung implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 100)
    @Column(name = "hoten")
    private String hoten;
    @Size(max = 100)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "dienthoai")
    private String dienthoai;
    @Size(max = 100)
    @Column(name = "diachi")
    private String diachi;
    @Column(name = "gioitinh")
    private Integer gioitinh;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ngaytao")
    @Temporal(TemporalType.DATE)
    private Date ngaytao;
    @Size(max = 255)
    @Column(name = "anh")
    private String anh;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
        @JoinColumn(name = "id_nguoidung", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_role", referencedColumnName = "id")})
    private Collection<Role> roleCollection;
    
    @OneToMany(mappedBy = "idNguoiduyet")
    private Collection<Donhang> donhangCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNguoidung")
    private Collection<Donhang> donhangCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNguoidung")
    private Collection<Sanphamyeuthich> sanphamyeuthichCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNguoidung")
    private Collection<Chitietdonhang> chitietdonhangCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNguoidung")
    private Collection<Comment> commentCollection;

    public Nguoidung() {
    }

    public Nguoidung(Integer id) {
        this.id = id;
    }

    public Nguoidung(Integer id, String email, Date ngaytao, int status) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Integer getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(Integer gioitinh) {
        this.gioitinh = gioitinh;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }

    public Collection<Donhang> getDonhangCollection() {
        return donhangCollection;
    }

    public void setDonhangCollection(Collection<Donhang> donhangCollection) {
        this.donhangCollection = donhangCollection;
    }

    public Collection<Donhang> getDonhangCollection1() {
        return donhangCollection1;
    }

    public void setDonhangCollection1(Collection<Donhang> donhangCollection1) {
        this.donhangCollection1 = donhangCollection1;
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

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
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
        if (!(object instanceof Nguoidung)) {
            return false;
        }
        Nguoidung other = (Nguoidung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dangducton.entities.Nguoidung[ id=" + id + " ]";
    }
    
}
