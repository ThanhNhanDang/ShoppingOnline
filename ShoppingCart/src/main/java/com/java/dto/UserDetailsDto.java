package com.java.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsDto extends User implements UserDetails{

	private static final long serialVersionUID = 1L;

	public UserDetailsDto(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super(email, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
}
