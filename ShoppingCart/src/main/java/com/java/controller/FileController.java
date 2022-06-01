package com.java.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.contants.ImageConstants;
import com.java.entity.FileDB;
import com.java.model.Message;
import com.java.service.FileService;

@RestController
@RequestMapping("api/upload/")
public class FileController{
	private FileService fileService;
	
	
	
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/base")
	public ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @RequestParam("nameCate") String nameCate,@RequestParam("nameFolder") String nameFolder) {
		try {
			
			if (fileService.uploadFileProduct(file, ImageConstants.URL_IMAGE_BASE, nameCate, nameFolder) == -1)
				return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/get-all")
	public ResponseEntity<List<FileDB>> getAllFileDB(){
		return new ResponseEntity<>(fileService.getAllFileDB(),HttpStatus.OK);
	}
	
	@PostMapping("/user-default-image")
	public Object userDefault (@RequestParam("file") MultipartFile file) {
		try {
			fileService.store(file, "userDefault");
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/product-default")
	public Object productDefault (@RequestParam("file") MultipartFile file) {
		try {
			//if (fileService.uploadFileUserDefault(file, ImageConstants.URL_IMAGE_BASE + "/upload-directory", "upload-directory.png") == -1)
			//return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			fileService.store(file, "productDefault");
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping()
	public ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file) throws IOException {
		return new ResponseEntity<>(fileService.store(file),HttpStatus.CREATED);
	}
	

	@PutMapping("/update/image/{fileId}")
	public ResponseEntity<?> updateImage(@RequestParam MultipartFile file, @PathVariable("fileId") long fileId){
		try {
			return new ResponseEntity<>(fileService.storeUpdate(file, fileId),HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/product-detail/update/image"), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@DeleteMapping("delete/{fileId}")
	public ResponseEntity<?> delete(@PathVariable("fileId") long fileId){
		fileService.deleteFileDB(fileId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}