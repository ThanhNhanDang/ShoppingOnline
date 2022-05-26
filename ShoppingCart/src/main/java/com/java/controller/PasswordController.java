package com.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.model.PasswordReset;
import com.java.service.UserService;

@RestController
@RequestMapping("api/user")
public class PasswordController {
	private UserService service;

	public PasswordController(UserService service) {
		this.service = service;
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword (@RequestBody PasswordReset passwordReset){
		try {
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
}
