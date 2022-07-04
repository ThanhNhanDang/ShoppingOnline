package com.java.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	int uploadFileProduct (MultipartFile file, String url, long cateId, long id) throws IOException;
	int uploadFileProduct (MultipartFile file, String url, String cateId, String id) throws IOException;
	int uploadFileUser (MultipartFile file, String url, long id) throws IOException;
	int uploadFileUserDefault (MultipartFile file, String url, String fileName) throws IOException;
	int deleFolder(long id, String location) throws IOException;
	int delefile(long id, String location, String urlImg) throws IOException;
	void updateImageDetail (MultipartFile file, long cateId, long id) throws IOException;
	
	void store(MultipartFile file, String fileName, int type, long id) throws IOException;
	void store(MultipartFile file) throws IOException;
	
}
