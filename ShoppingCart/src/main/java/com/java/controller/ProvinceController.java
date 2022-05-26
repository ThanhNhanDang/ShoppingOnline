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
import com.java.entity.Province;
import com.java.model.Message;
import com.java.service.ProvinceService;

@RestController
@RequestMapping("/api/Vietnamese-Administrative-Unit")
public class ProvinceController {
	private ProvinceService service;

	public ProvinceController(ProvinceService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/provinces/get-all")
	public ResponseEntity<?> getAllProvince(){
		try {
			List<Province> list = service.getAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/get-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/provinces/by-id")
	public ResponseEntity<?> getProvince(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"provinceId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long id = Long.valueOf(request.get("provinceId"));
			Province dto = service.find(id);
			if(dto == null)
				return new ResponseEntity<>(new Message("This province was not found", Instant.now(), "400", "Bad Request", "/api/Vietnamese-Administrative-Unit/provinces/by-id"), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("This province was not found", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/by-id"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/provinces/save")
	public ResponseEntity<?> save(@RequestBody Province province){
		try {
			Province entity = service.save(province);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/save"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/provinces/save-all")
	public ResponseEntity<?> saveAll(@RequestBody List<Province> provinces){
		try {
			service.saveAll(provinces);
			return new ResponseEntity<> (HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/save-all"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/provinces/delete")
	public ResponseEntity<?> deleteProvince(@RequestParam long id){
		try {
			List<Province> list = service.delete(id);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/delete"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/provinces/update")
	public ResponseEntity<?> updateProvince(@RequestBody Province entity){
		try {
			service.update(entity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/Vietnamese-Administrative-Unit/provinces/update"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/provinces/search")
	public ResponseEntity<?> getAllProvinceByKey(@RequestParam String key){
		List<Province> list = service.search(key);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
