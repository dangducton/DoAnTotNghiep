package com.dangducton.dto;

public class NguoiDungSignUpDTO {
	private String hoten;
	private String email;
	private String password;
	private String confirmpassword;

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public NguoiDungSignUpDTO() {
	}

	public NguoiDungSignUpDTO(String hoten, String email, String password, String confirmpassword) {
		super();
		this.hoten = hoten;
		this.email = email;
		this.password = password;
		this.confirmpassword = confirmpassword;
	}

}
