package com.java.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.java.config.ShoppingConfig;
import com.java.contants.ImageConstants;
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
	
	@PostMapping("/add/image")
	public ResponseEntity<?> addProductDetail(@RequestParam MultipartFile file, @RequestParam long cateId, @RequestParam long productId){
		try {
			fileService.uploadFileProduct(file, ImageConstants.URL_IMAGE_PRODUCT, cateId, productId);
				
			return new ResponseEntity<>(HttpStatus.CREATED); 
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
	
	@PostMapping("/update/image")
	public ResponseEntity<?> updateImage(@RequestParam MultipartFile file, @RequestParam long cateId, @RequestParam long productId){
		try {
			fileService.updateImageDetail(file, cateId, productId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/update/image"), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam long cateId, @RequestParam long productId, @RequestParam long id, @RequestParam String urlImg){
		try {
			if(fileService.delefile(cateId, productId, urlImg)==0);
				service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/delete"), HttpStatus.BAD_REQUEST);
			
		}
	}
}
