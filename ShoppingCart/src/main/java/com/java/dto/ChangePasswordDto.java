package com.java.dto;

public class ChangePasswordDto {
	private String email;
	private String oldPassword;
	private String confirmPassword;
	
	public ChangePasswordDto() {
		// TODO Auto-generated constructor stub
	}
	public ChangePasswordDto(String email, String oldPassword, String confirmPassword) {
		this.email = email;
		this.oldPassword = oldPassword;
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	
	
}
