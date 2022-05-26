package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.ShoppingConfig;
import com.java.entity.District;
import com.java.entity.Ward;
import com.java.model.Message;
import com.java.service.WardService;

@RestController
@RequestMapping("/api/Vietnamese-Administrative-Unit")
public class WardsController {
	private WardService service;

	public WardsController(WardService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/wards/get-all")
	public ResponseEntity<?> getAllDistrict(){
		try {
			List<Ward> list = service.getAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/get-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/wards/get-all-by-district-id")
	public ResponseEntity<?> getAllDistrict(@RequestBody HashMap<String, String> request){
		try {
			List<Ward> list = service.findWardByDistrictId(Long.valueOf(request.get("districtId")));
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/get-all-by-district-id"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/wards/by-id")
	public ResponseEntity<?> getDistrict(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"wardId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long id = Long.valueOf(request.get("wardId"));
			Ward dto = service.find(id);
			if(dto == null)
				return new ResponseEntity<>(new Message("This district was not found", Instant.now(), "400", "Bad Request", "/api/Vietnamese-Administrative-Unit/wards/by-id"), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("This district was not found", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/by-id"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/wards/save")
	public ResponseEntity<?> save(@RequestBody Ward district){
		try {
			Ward entity = service.save(district);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/save"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/wards/save-all")
	public ResponseEntity<?> saveAll(@RequestBody List<Ward> districts){
		try {
			service.saveAll(districts);
			return new ResponseEntity<> (HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/save-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/wards/delete")
	public ResponseEntity<?> deleteDistrict(@RequestParam long id){
		try {
			List<Ward> list = service.delete(id);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/delete"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/wards/update")
	public ResponseEntity<?> updateDistrict(@RequestBody Ward entity){
		try {
			service.update(entity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/wards/update"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/wards/search")
	public ResponseEntity<?> getAllByKey(@RequestParam String key){
		List<Ward> list = service.search(key);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
