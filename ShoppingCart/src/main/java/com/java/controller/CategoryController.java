package com.java.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.CategoryDto;
import com.java.model.Message;
import com.java.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> get(){
		try {
			List<CategoryDto> categoryDtos = categoryService.findAll();
			if (categoryDtos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(categoryDtos ,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/update-all-category")
	public ResponseEntity<?> updateAllCategory (@RequestBody List<CategoryDto> categoryDtos){
		try {
			categoryService.updateAllCategory(categoryDtos);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad request", "/api/category/update-all-category"),HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add (@RequestBody CategoryDto dto){
		try {
			categoryService.save(dto);
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad request", "/api/category/add"),HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete (@PathVariable ("id") long id){
		try {
			List<CategoryDto> categoryDtos = categoryService.delete(id);
			return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad request", "/api/category/delete"), HttpStatus.BAD_REQUEST);
		}
		
	}

}
