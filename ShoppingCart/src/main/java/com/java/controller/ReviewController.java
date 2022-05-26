package com.java.controller;

import java.time.Instant;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.ShoppingConfig;
import com.java.dto.ReviewDto;
import com.java.dto.ReviewDtoSize;
import com.java.model.Message;
import com.java.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	private ReviewService service;
	 
	
	public ReviewController(ReviewService service) {
		this.service = service;
	}


	@PostMapping("/add")
	public ResponseEntity<?> sendMessage(@RequestBody ReviewDto reviewDto) {
		try {
			ReviewDto dto = service.save(reviewDto);
			if(dto == null) 
				return new ResponseEntity<> (new Message("Can't add", Instant.now(), "500", "Internal Server Error", "/api/review/add"),HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<> (new Message("This product was not found", Instant.now(), "500", "Internal Server Error", "/api/review/add"),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/get/by-user")
	public ResponseEntity<?> getByUser(@RequestBody HashMap<String, String> request) {
		try {
			String keys []= {"userId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			
			long userId = Long.valueOf(request.get("userId"));
			ReviewDtoSize dtoSize = service.findAllReviewByUser(userId);
			if(dtoSize==null)
				return new ResponseEntity<> (new Message("You don't have any reviews yet.", Instant.now(), "204", "No Content", "/api/review/get/by-user"),HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(dtoSize, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<> (new Message("This product was not found", Instant.now(), "500", "Internal Server Error", "/api/review/get/by-user"),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/get/by-product")
	public ResponseEntity<?> getByUserAndProduct(@RequestBody HashMap<String, String> request) {
		try {
			String keys []= {"productId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			
			long productId = Long.valueOf(request.get("productId"));
			ReviewDtoSize dtoSize = service.findAllReviewByProduct(productId);
			if(dtoSize==null)
				return new ResponseEntity<> (new Message("The product has no reviews yet.", Instant.now(), "204", "No Content", "/api/review/get/by-product"),HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(dtoSize, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<> (new Message("This product was not found", Instant.now(), "500", "Internal Server Error","/api/review/get/by-product"),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	@PostMapping("/delete-my-review")
	public ResponseEntity<?> delete(@RequestBody HashMap<String, String> request) {
		try {
			String keys []= {"userId, productId, reviewId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			
			long userId = Long.valueOf(request.get("userId"));
			long productId = Long.valueOf(request.get("productId"));
			long reviewId = Long.valueOf(request.get("reviewId"));
			if(service.deleteReviewByUserAndProduct(userId, productId, reviewId) == -1)
				return new ResponseEntity<> (new Message("No products found to delete", Instant.now(), "400", "Bad Request", "/api/review/delete-my-review"),HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<> (new Message("Server lỗi", Instant.now(), "500", "Internal Server Error","/api/review/delete-my-review"),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}


