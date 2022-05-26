package com.java.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ResetPasswordTokenEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String token; 
	
	@OneToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User user;

	public ResetPasswordTokenEntity() {}
	public ResetPasswordTokenEntity(long id, String token) {
		this.id = id;
		this.token = token;
	}
	public ResetPasswordTokenEntity(String token, User user) {
		this.token = token;
		this.user = user;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
