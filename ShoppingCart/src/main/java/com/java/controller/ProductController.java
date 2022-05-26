package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.config.ShoppingConfig;
import com.java.contants.ImageConstants;
import com.java.dto.ProductsDto;
import com.java.entity.Products;
import com.java.model.Message;
import com.java.model.SearchAndSort;
import com.java.service.FileService;
import com.java.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	private ProductService productService;
	private FileService fileService;
	public ProductController(ProductService productService, FileService fileService) {
		this.productService = productService;
		this.fileService = fileService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> get (){
			List<ProductsDto> dtos = productService.findAll();
			if (dtos.isEmpty())
				return new ResponseEntity<>(new Message("No products not be found", Instant.now(), "400", "Bad request", "/api/product/all"), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/get-products-by-category")
	public ResponseEntity<?> getProductsByCategory(
			@RequestParam(required = false) String id,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "9") int size){
		 Page<ProductsDto> dtos = null;
		try {
			Pageable pageable = PageRequest.of(page, size);
			if (id == null) {
				dtos = productService.getAllProducts(pageable);
				return new ResponseEntity<>(dtos, HttpStatus.OK);
				
			}
			long categoryId = Long.valueOf(id);
			dtos = productService.getProductsByCategoryId(categoryId, pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message("This product was not found", Instant.now(), "500", "Internal Server Error", "/api/product/get-products-by-category"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add (@RequestBody ProductsDto dto ){
		try {
			Products entity = productService.saveReturn(dto);
			if(entity == null)
				throw new Exception("Can't Add");
			return new ResponseEntity<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "500", "Internal Server Error", "/api/product/get-products-by-category"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/get-product")
	public ResponseEntity<?> getProduct (@RequestBody HashMap<String, String> request){
		try {
			String keys[] = {"productId"};
			if(ShoppingConfig.validationWithHashMap(keys, request)) {}
			long productId = Long .valueOf(request.get("productId"));
			ProductsDto dto = productService.findById(productId);
			if(dto == null)
				return new ResponseEntity<>(new Message("This product was not found", Instant.now(), "400", "Bad Request", "/api/product/get-product"), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("This product was not found", Instant.now(), "500", "Internal Server Error", "/api/product/get-product"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/post-img{id}")
	public ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @RequestParam("cateId") long cateId, @RequestParam("id") long id) {
		try {
			
			if (fileService.uploadFileProduct(file, ImageConstants.URL_IMAGE_PRODUCT, cateId, id) == -1)
				return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductsDto dto){
		try {
			
			int check = productService.editMyAccout(dto);
			if(check == -1) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product/update"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/delete/all-by-select")
	public ResponseEntity<?> delete (@RequestBody List<ProductsDto> dtos){
		try {
			List<ProductsDto> dtos2 = productService.deleteAllBySelect(dtos);
			if (dtos == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(dtos2, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product/delete/all-by-select"), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping("/search")
	public @ResponseBody Page<ProductsDto> search(@RequestBody SearchAndSort request){
			Pageable pageable;
			// true là mới nhất
			
			if(request.isOldOrNew())
				pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortKey()).descending());
			else 
				pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortKey()));
			Page<ProductsDto> dtos = productService.getProductsSearch(pageable, request.getKey(), request.getCateId());
			return dtos;
	}
	

	@GetMapping("/search/admin")
	public @ResponseBody List<ProductsDto> searchAdmin(@RequestParam String key){
			List<ProductsDto> dtos = productService.searchAdmin(key);
			return dtos;
	}
	

}
