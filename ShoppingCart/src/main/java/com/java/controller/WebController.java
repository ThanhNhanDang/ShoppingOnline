package com.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.service.WebService;

@RestController
@RequestMapping("/api/dashboad/get-data")
public class WebController {
	
	private WebService webService;
	
	public WebController(WebService webService) {
		super();
		this.webService = webService;
	}

	@GetMapping("/province")
	public String dashboad() {
		return "pong";
	}
	
	@GetMapping("/product-with-the-most-orders")
	public ResponseEntity<?> dashboadProductWithTheMostOrders() {
		return new ResponseEntity<>(this.webService.findProvincesWithTheMostOrders(), HttpStatus.OK);
	}

}
