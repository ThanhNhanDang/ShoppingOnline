package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.ShoppingConfig;
import com.java.dto.WishListDto;
import com.java.dto.WishListDtoSize;
import com.java.model.Message;
import com.java.service.WishListService;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {
	private WishListService service;

	public WishListController(WishListService service) {
		this.service = service;
	}
	
	@PostMapping("/get-all-by-user")
	public ResponseEntity<?> getAllByUser(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"userId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long userId = Long.valueOf(request.get("userId"));
			List<WishListDto> dtos = service.getAllByUser(userId);
			return new ResponseEntity<>(new WishListDtoSize(dtos, dtos.size()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/wishlist/get-all-by-user"),HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"userId","productId", "quantity"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			if(service.addByUserAndProduct(Long.valueOf(request.get("userId")), 
					Long.valueOf(request.get("productId")),Integer.valueOf(request.get("quantity")))
				== -1){
				return new ResponseEntity<>(new Message("Can't Add", Instant.now(), "400", "Bad Request", "/api/wishlist/add"),HttpStatus.BAD_REQUEST);
			};
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/wishlist/add"),HttpStatus.BAD_REQUEST);

		}
	}
	
	@DeleteMapping("/deleteByUser")
	public ResponseEntity<?> deleteByUser(
			@RequestParam String userId,
	        @RequestParam String id){
		try {
			List<WishListDto> dtos = service.delete(Long.valueOf(id), Long.valueOf(userId));
			return new ResponseEntity<>(new WishListDtoSize(dtos, dtos.size()),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/wishlist/add"),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@PostMapping("/get-item")
	public ResponseEntity<?> getItem(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"userId","wishlistId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long userId = Long.valueOf(request.get("userId"));
			long wishlistId = Long.valueOf(request.get("wishlistId"));
			System.out.println(userId + "/" + wishlistId);
			WishListDto dto = service.getItem(userId, wishlistId);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "500", "Internal Server Error", "/api/wishlist/get-item"),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
}
