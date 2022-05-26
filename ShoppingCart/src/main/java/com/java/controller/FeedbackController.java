package com.java.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.entity.Feedback;
import com.java.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	private FeedbackService service;

	public FeedbackController(FeedbackService service) {
		super();
		this.service = service;
	}

	@GetMapping("/get-all")
	public ResponseEntity<List<Feedback>> getAll() {
		return new ResponseEntity<List<Feedback>>(this.service.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> savee(@RequestBody Feedback feedback) {
		this.service.save(feedback);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
