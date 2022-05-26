package com.java.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpLoadFileService {
	String upload (MultipartFile file);
}
