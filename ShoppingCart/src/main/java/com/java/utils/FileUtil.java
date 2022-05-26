package com.java.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	public static void fileupload(InputStream inputStream,Path uploadPath,String fileName) throws IOException {
		 // Target directory		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		Path filePath = uploadPath.resolve(fileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	}
	public static int deleteFile(String path) throws IOException {
		File delFile;
		delFile = new File(path);
		if(delFile.exists()) {
			// Xóa tất cả các file và thư mục nằm dưới nó
			FileUtils.cleanDirectory(delFile);
			//sau đó xóa thư mục
			delFile.delete();
			return 0;
		}
		return -1;
	}
	public static int deleteImgDetail(String path, String imagePath) throws IOException {
		File delFile;
		File delFile2;
		delFile = new File(path);
		delFile2 = new File(imagePath);
		if(delFile.exists()) {
			if(delFile2.exists()) {	
				delFile2.delete();
			}
			return 0;
		}
		return -1;
	}
	
	public static void fileUpdate(InputStream inputStream,Path uploadPath,String fileName) throws IOException {
		 // Target directory		
		if(!Files.exists(uploadPath)) {
			throw new IOException("Not found");
		}
		Path filePath = uploadPath.resolve(fileName);
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	}
}
