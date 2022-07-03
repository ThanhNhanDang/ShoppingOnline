package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.java.dto.ProductDetailDto;
import com.java.model.Message;
import com.java.service.ProductDetailService;

@RestController
@RequestMapping("/api/product-detail")
public class ProductDetailController {
	private ProductDetailService service;

	public ProductDetailController(ProductDetailService service) {
		this.service = service;
	}

	@PostMapping("/get-product-detail-by-product")
	public ResponseEntity<?> getProductDetailByProduct(@RequestBody HashMap<String, String> request) {
		try {
			String keys[] = { "productId", "cateId" };
			if (ShoppingConfig.validationWithHashMap(keys, request)) {
			}
			List<ProductDetailDto> dtos = service.getProductDetailByProduct(Long.valueOf(request.get("productId")),
					Long.valueOf(request.get("cateId")));
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "500", "Internal Server Error",
					"/api/product-detail/get-product-detail-by-product"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addProductDetail(@RequestParam("dto") String dto,
			@RequestParam("file") MultipartFile file) {
		try {
			ProductDetailDto dto2 = service.addProductDetail(dto, file);
			return new ResponseEntity<>(dto2, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/product-detail/add"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/add/all", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addAllProductDetail(@RequestParam("dto") String listDto,
			@RequestParam("file") List<MultipartFile> files) {
		try {
			List<ProductDetailDto> dtos = service.addAllProductDetail(listDto, files);
			return new ResponseEntity<>(dtos, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new Message("Server lỗi", Instant.now(), "500", "Internal Server Error", "/api/product-detail/add"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> update(@RequestParam("dto") String dto, @RequestParam("file") MultipartFile file) {
		try {
			service.updateProductDetail(dto, file);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/update"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/update/all", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateAll(@RequestParam("dto") String listDto,
			@RequestParam("file") List<MultipartFile> files) {
		try {
			service.updateAllProductDetail(listDto, files);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/update"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/delete"),
					HttpStatus.BAD_REQUEST);

		}
	}
	@DeleteMapping("/delete/all")
	public ResponseEntity<?> deleteAllByProductId(@RequestParam long productId) {
		try {
			service.deleteAllByProductId(productId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/delete"),
					HttpStatus.BAD_REQUEST);

		}
	}
}
