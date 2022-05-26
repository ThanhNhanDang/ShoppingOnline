package com.java.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.RoleDto;
import com.java.model.Message;
import com.java.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	private RoleService service;

	public RoleController(RoleService service) {
		this.service = service;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		try {
			List<RoleDto> dtos = service.findAll();;
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			
			return new ResponseEntity<>(new Message("Error", Instant.now(), "400", "Bad request", "/api/role/all"), HttpStatus.BAD_REQUEST);
		}
	
	}
	
	
}
