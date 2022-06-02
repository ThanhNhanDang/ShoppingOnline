package com.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
	
	@GetMapping("/dashboad/get-data")
	public String dashboad() {
		return "pong";
	}

}
