package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.ShoppingConfig;
import com.java.dto.CheckOutCartDto;
import com.java.model.Message;
import com.java.service.CartService;
import com.java.service.DeliveryAddressService;

@RestController
@RequestMapping("/api/order")
public class CheckOutController {
	private CartService cartService;
	private DeliveryAddressService deliveryAddressService;	
	
	public CheckOutController(CartService cartService, DeliveryAddressService deliveryAddressService) {
		this.cartService = cartService;
		this.deliveryAddressService = deliveryAddressService;
	}
	@PostMapping("/checkout-order")
	public ResponseEntity<?> checkoutOrder(@RequestBody HashMap<String, String> checkoutOrderRequest){
		return deliveryAddressService.checkOut(checkoutOrderRequest);
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<List<CheckOutCartDto>> getAll(){
		List<CheckOutCartDto> checkOutDtos = cartService.getAll();
		return new ResponseEntity<>(checkOutDtos, HttpStatus.OK);
	}
	
	@PostMapping("/get-my-orders")
	public ResponseEntity<?> getOrdersByUserId(@RequestBody HashMap<String, String> getOrdersRequest){
		try {
			String keys[] = {"userId"};
			long userId = Long.valueOf(getOrdersRequest.get("userId"));
			if(ShoppingConfig.validationWithHashMap(keys, getOrdersRequest)) {}
			List<CheckOutCartDto> checkOutDtos = cartService.getCheckOutCartByUserId(userId);
			return new ResponseEntity<>(checkOutDtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/order/get-my-orders"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	public ResponseEntity<?> detele(@RequestBody HashMap<String, String> updateOderRequest){
//		try {
//			String keys[] = {"cart"};
//			if(ShoppingConfig.validationWithHashMap(keys, updateOderRequest)) {}
//			return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/order/get-my-orders"),HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	public ResponseEntity<?> add(@RequestBody HashMap<String, String> updateOderRequest){
//		try {
//			String keys[] = {"cart"};
//			if(ShoppingConfig.validationWithHashMap(keys, updateOderRequest)) {}
//			return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.BAD_REQUEST);
//		}
//	}
}
