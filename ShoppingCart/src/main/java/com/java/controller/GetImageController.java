package com.java.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.entity.FileDB;
import com.java.service.FileService;
import com.java.utils.ImageUtil;

@RestController
@RequestMapping("api/get/image")
public class GetImageController {
	private FileService fileService;
	
	public GetImageController(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) throws IOException {
		
	        final Optional<FileDB> dbImage = fileService.findById(id);

	        return ResponseEntity
	                .ok()
	                .contentType(MediaType.valueOf(dbImage.get().getType()))
	                .body(ImageUtil.decompressImage(dbImage.get().getData()));
	    }
}
