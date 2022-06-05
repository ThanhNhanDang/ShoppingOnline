package com.java.dto;

public class ResetPasswordDto {
	private String token;
	private String newPassword;

	public ResetPasswordDto() {
		// TODO Auto-generated constructor stub
	}

	public ResetPasswordDto(String token, String newPassword) {
		super();
		this.token = token;
		this.newPassword = newPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
