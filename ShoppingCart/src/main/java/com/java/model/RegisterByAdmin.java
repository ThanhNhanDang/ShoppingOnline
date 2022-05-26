package com.java.model;

public class RegisterByAdmin {
	
	private String	name;
	private String	email;
	private String 	mobile;
	private String 	gender;
	private int		role_id;
	private boolean is_email_verfied;
	private String 	password;
	
	public RegisterByAdmin() {
		// TODO Auto-generated constructor stub
	}
	
	public RegisterByAdmin(String name, String email, String mobile, String gender, int role_id,
			boolean is_email_verfied, String password) {
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.role_id = role_id;
		this.is_email_verfied = is_email_verfied;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public boolean isIs_email_verfied() {
		return is_email_verfied;
	}

	public void setIs_email_verfied(boolean is_email_verfied) {
		this.is_email_verfied = is_email_verfied;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
