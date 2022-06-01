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
import com.java.dto.ProductDetailDto;
import com.java.model.Message;
import com.java.service.FileService;
import com.java.service.ProductDetailService;

@RestController
@RequestMapping("/api/product-detail")
public class ProductDetailController {
	private ProductDetailService service;
	private FileService fileService;
	public ProductDetailController(ProductDetailService service, FileService fileService) {
		this.service = service;
		this.fileService = fileService;
	}
	
	@PostMapping("/get-product-detail-by-product")
	public ResponseEntity<?> getProductDetailByProduct(@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"productId", "cateId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			List<ProductDetailDto> dtos = service.getProductDetailByProduct(Long.valueOf(request.get("productId")), Long.valueOf(request.get("cateId")));
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "500", "Internal Server Error", "/api/product-detail/get-product-detail-by-product"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addProductDetail(@RequestBody ProductDetailDto detailDto){
		try {
			ProductDetailDto dto = service.addProductDetail(detailDto);
			return new ResponseEntity<>(dto, HttpStatus.CREATED); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/product-detail/add"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductDetailDto dto){
		try {
			service.update(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/update"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam long id, @RequestParam long fileId){
		try {
			service.delete(id);
			fileService.deleteFileDB(fileId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/delete"), HttpStatus.BAD_REQUEST);
			
		}
	}
}
