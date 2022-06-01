package com.java.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.java.entity.FileDB;

public interface FileService {
	int uploadFileProduct (MultipartFile file, String url, long cateId, long id) throws IOException;
	int uploadFileProduct (MultipartFile file, String url, String cateId, String id) throws IOException;
	int uploadFileUser (MultipartFile file, String url, long id) throws IOException;
	int uploadFileUserDefault (MultipartFile file, String url, String fileName) throws IOException;
	int delefile(long cateId, long id) throws IOException;
	int delefile(long cateId, long id, String urlImg) throws IOException;
	int delefileUser(long id) throws IOException;
	void updateImageDetail (MultipartFile file, long cateId, long id) throws IOException;
	
	
	List<FileDB>getAllFileDB();
	Optional<FileDB> findById(long id);
	FileDB store(MultipartFile file, String fileName) throws IOException;
	FileDB store(MultipartFile file, long id) throws IOException;
	FileDB store(MultipartFile file) throws IOException;
	FileDB storeUpdate(MultipartFile file, long fileId) throws IOException;
	void deleteFileDB (long id);
}
