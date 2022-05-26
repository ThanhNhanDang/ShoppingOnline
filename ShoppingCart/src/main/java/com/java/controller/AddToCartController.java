package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.ShoppingConfig;
import com.java.dto.AddToCartDto;
import com.java.dto.AddToCartDtoSize;
import com.java.model.Message;
import com.java.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class AddToCartController {
	
	private CartService cartService;
	public AddToCartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<?> addCartWithProduct (@RequestBody HashMap<String, String> addCartRequest){
		try {
			String keys[] = {"productId", "userId", "quantity"};
			System.out.println(Long.valueOf(addCartRequest.get("productId")));
			if(ShoppingConfig.validationWithHashMap(keys, addCartRequest)) {}
			
			long 	productId 	= Long.valueOf(addCartRequest.get("productId"));
			long 	userId 		= Long.valueOf(addCartRequest.get("userId"));
			int 	quantity 	= Integer.valueOf(addCartRequest.get("quantity"));
			List<AddToCartDto>  dtos = cartService.addCartByUserIdAndProductId(userId, productId, quantity);
			return new ResponseEntity<>(dtos, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad requesr", "/api/cart/add-product"),HttpStatus.BAD_REQUEST);
		}

	}
	
	@DeleteMapping("/delete-product-from-cart")
	public ResponseEntity<?> deleteCartWithProductId (
			@RequestParam String userId,
	        @RequestParam String cartId){
		try {
			List<AddToCartDto> dtos = cartService.deleteCartByUserId(Long.valueOf(cartId), Long.valueOf(userId));
			return new ResponseEntity<>(new AddToCartDtoSize(dtos, dtos.size()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/cart/delete-product-from-cart"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/get-cart")
	public ResponseEntity<?> getCartByUserId (@RequestBody HashMap<String, String> getCartReques){
		try {
			String keys[] = {"userId"};
			if(ShoppingConfig.validationWithHashMap(keys, getCartReques)) {}
			List<AddToCartDto> dtos = cartService.getCartByUserId(Long.valueOf(getCartReques.get("userId")));
			return new ResponseEntity<>(new AddToCartDtoSize(dtos, dtos.size()), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/cart/update-quantity-for-cart"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update-all-quantity-for-cart")
	public ResponseEntity<?> updateQtyByCartId (@RequestBody List<AddToCartDto> updateRequest){
		try {
			
			cartService.updateQtyByCartId(updateRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/cart/update-quantity-for-cart"),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	@PutMapping("/update-quantity-for-cart")
	public ResponseEntity<?> update (@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {
				      "id",
				      "price",
				      "quantity" };
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			double price = Double.valueOf(request.get("price"));
			long id = Long.valueOf(request.get("id"));
			int quantity = Integer.valueOf(request.get("quantity"));
			cartService.updateItem(price, id, quantity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/cart/update-quantity-for-cart"),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/get-product-of-cart")
	public ResponseEntity<?> getProductOfCart(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"userId", "cartId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long cartId = Long.valueOf(request.get("cartId"));
			long userId = Long.valueOf(request.get("userId"));
			AddToCartDto dto = cartService.getCartById(userId,cartId);
			if(dto == null) 
				throw new Exception("This product was not found.");
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "500", "Internal Server Error", "/api/cart/get-product-of-cart"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
