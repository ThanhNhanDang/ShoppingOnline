package com.java.service;

import com.java.dto.TokenDto;
import com.java.model.AuthenticationResponse;
import com.java.model.LoginRequest;
import com.java.model.RegisterRequest;

public interface AuthService {
	AuthenticationResponse login(LoginRequest dto) throws Exception ;
	AuthenticationResponse loginGoogle(TokenDto dto)throws Exception ;
	AuthenticationResponse loginFacebook(TokenDto dto)throws Exception ;
	void signup (RegisterRequest dto) throws Exception;
	
}	
