package com.java.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.TokenDto;
import com.java.model.RegisterRequest;
import com.java.service.AuthService;
import com.java.service.UserService;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {
	
	private AuthService authService;
	private UserService userService;
	public SignUpController(AuthService authService, UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}
	
	@PostMapping("/user")
	public  ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest) throws Exception {
		authService.signup(registerRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/user/google")
	public ResponseEntity<?> signupGoogle(@RequestBody TokenDto dto) throws IOException, MessagingException{
		userService.signUpWithSocialMediaGoogle(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/user/facebook")
	public ResponseEntity<?> signupFacebook(@RequestBody TokenDto dto) throws IOException, MessagingException{
		userService.signUpWithSocialMediaFacebook(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
}
