package com.java.controller;

import java.time.Instant;
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

import com.java.dto.DeliveryAddressDto;
import com.java.model.Message;
import com.java.service.DeliveryAddressService;


@RestController
@RequestMapping("/api/delivery-address")
public class DeliveryAddressController {
	private DeliveryAddressService service;

	public DeliveryAddressController(DeliveryAddressService service) {
		this.service = service;
	}
	
	@PostMapping("/get-all-by-user")
	public ResponseEntity<?> getDeliveryAddressByUser(@RequestParam long userId) {
		try {
			List<DeliveryAddressDto> dtos = service.findByUser(userId);
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
					
		
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody DeliveryAddressDto dto){
		try {
			service.update(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/delivery-address/update"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam long id, @RequestParam long userId, @RequestParam long deliveryAddressId){
		try {
			System.out.println("id"+id+ "use"+userId+ "de"+deliveryAddressId);
			service.delete(id, userId, deliveryAddressId);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/delivery-address/delete"), HttpStatus.BAD_REQUEST);
		}
	}
	
}
