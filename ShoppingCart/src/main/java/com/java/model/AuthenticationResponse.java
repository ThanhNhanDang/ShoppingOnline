package com.java.model;

import com.java.dto.UserDto;

public class AuthenticationResponse {
	private String authenticationToken;
	private UserDto userProfile;
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	
	public AuthenticationResponse(String authenticationToken, UserDto userProfile) {
		this.authenticationToken = authenticationToken;
		this.userProfile = userProfile;
	}
	
	public UserDto getUserProfile() {
		return userProfile;
	}
	
}
