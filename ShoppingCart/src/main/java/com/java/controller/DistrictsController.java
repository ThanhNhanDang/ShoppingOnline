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
import com.java.model.Message;
import com.java.service.DistrictService;

@RestController
@RequestMapping("/api/Vietnamese-Administrative-Unit")
public class DistrictsController {
	
	private DistrictService service;
	
	public DistrictsController(DistrictService service) {
		super();
		this.service = service;
	}

	@GetMapping("/districts/get-all")
	public ResponseEntity<?> getAllDistrict(){
		try {
			List<District> list = service.getAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/get-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/districts/get-all-by-province-id")
	public ResponseEntity<?> getAllDistrict(@RequestBody HashMap<String, String> request){
		try {
			List<District> list = service.findDistrictByProvinceId(Long.valueOf(request.get("provinceId")));
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/get-all-by-province-id"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/districts/by-id")
	public ResponseEntity<?> getDistrict(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"districtId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long id = Long.valueOf(request.get("districtId"));
			District dto = service.find(id);
			if(dto == null)
				return new ResponseEntity<>(new Message("This district was not found", Instant.now(), "400", "Bad Request", "/api/Vietnamese-Administrative-Unit/districts/by-id"), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("This district was not found", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/by-id"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/districts/save")
	public ResponseEntity<?> save(@RequestBody District district){
		try {
			District entity = service.save(district);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/save"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/districts/save-all")
	public ResponseEntity<?> saveAll(@RequestBody List<District> districts){
		try {
			service.saveAll(districts);
			return new ResponseEntity<> (HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/save-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/districts/delete")
	public ResponseEntity<?> deleteDistrict(@RequestParam long id){
		try {
			List<District> list = service.delete(id);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/delete"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/districts/update")
	public ResponseEntity<?> updateDistrict(@RequestBody District entity){
		try {
			service.update(entity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/districts/update"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/districts/search")
	public ResponseEntity<?> getAllByKey(@RequestParam String key){
		List<District> list = service.search(key);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
