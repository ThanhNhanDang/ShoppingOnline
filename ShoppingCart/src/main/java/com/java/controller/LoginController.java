package com.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.TokenDto;
import com.java.model.AuthenticationResponse;
import com.java.model.LoginRequest;
import com.java.service.AuthService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	private AuthService authService;
	
	public LoginController(AuthService authService) {
		this.authService = authService;
	}
	@PostMapping("/user")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
		AuthenticationResponse authenticationResponse =  authService.login(loginRequest);
		return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
	}
	
	@PostMapping("/user/google")
	public ResponseEntity<?> loginGoole(@RequestBody TokenDto dto) throws Exception {
		AuthenticationResponse authenticationResponse;
		authenticationResponse = authService.loginGoogle(dto);
		return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);

		
	}
	
	@PostMapping("/user/facebook")
	public ResponseEntity<?> loginFacebook(@RequestBody TokenDto dto) throws Exception{
		
		AuthenticationResponse authenticationResponse;
		authenticationResponse = authService.loginFacebook(dto);
		return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);	
	}
}
