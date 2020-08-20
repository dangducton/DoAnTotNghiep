package com.dangducton.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "confirmationtoken")
public class ConfirmationToken implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "token_id")
	private Integer tokenid;;

	@Column(name="confirmation_token")
	private String confirmationToken;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	@OneToOne(targetEntity = Nguoidung.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_nguoidung",referencedColumnName = "id")
    private Nguoidung user;
	
	public ConfirmationToken() {
	}
	
	public ConfirmationToken(Nguoidung user) {
		this.user = user;
		createdDate = new Date();
		confirmationToken = UUID.randomUUID().toString();
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Nguoidung getUser() {
		return user;
	}

	public void setUser(Nguoidung user) {
		this.user = user;
	}

	public Integer getTokenid() {
		return tokenid;
	}

	public void setTokenid(Integer tokenid) {
		this.tokenid = tokenid;
	}
}

