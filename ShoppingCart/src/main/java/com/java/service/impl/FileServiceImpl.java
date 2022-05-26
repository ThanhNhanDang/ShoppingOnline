package com.java.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.java.contants.ImageConstants;
import com.java.service.FileService;
import com.java.utils.FileUtil;

@Service
@Scope("prototype")
public class FileServiceImpl implements FileService{

	@Override
	public int uploadFileProduct(MultipartFile file, String url, long cateId, long id) throws IOException {
		if(file.isEmpty()) 
			return -1;
			// Get the file name, including the suffix			
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());		
		Path uploadPath = Paths.get("./"+url + "/" + cateId+ "/"+ id + "/");	
			 // Store in this path: the path is under the static file in the project directory: (Note: this file may need to be created by yourself)
			 // The reason for putting it under static is that it stores static file resources, that is, you can enter the local server address through the browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);
		
		return 0;
	}

	@Override
	public int delefile(long cateId, long id) throws IOException {
		String realPath = Paths.get("./"+ImageConstants.URL_IMAGE_PRODUCT + cateId +"/"+id+"/").toString();
		return FileUtil.deleteFile(realPath);
	}

	@Override
	public int uploadFileUser(MultipartFile file, String url, long id) throws IOException {
		if(file.isEmpty()) 
			return -1;
			// Get the file name, including the suffix			
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());		
		Path uploadPath = Paths.get("./"+url + "/"+ id + "/");	
		
		
			 // Store in this path: the path is under the static file in the project directory: (Note: this file may need to be created by yourself)
			 // The reason for putting it under static is that it stores static file resources, that is, you can enter the local server address through the browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);
		
		return 0;
	}

	@Override
	public int delefileUser(long id) throws IOException {
		String realPath = Paths.get("./"+ImageConstants.URL_IMAGE_AVATAR +"/"+id+"/").toString();
		return FileUtil.deleteFile(realPath);
	}

	@Override
	public void updateImageDetail(MultipartFile file,long cateId,  long id) throws IOException {
		if(file.isEmpty())
			throw new IOException("File is empty");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());		
		Path uploadPath = Paths.get("./"+ImageConstants.URL_IMAGE_PRODUCT + "/" + cateId+ "/"+ id + "/");	
		FileUtil.fileUpdate(file.getInputStream(), uploadPath, fileName);
			
	}

	@Override
	public int uploadFileProduct(MultipartFile file, String url, String cateId, String id) throws IOException {
		if(file.isEmpty()) 
			return -1;
			// Get the file name, including the suffix			
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());		
		Path uploadPath = Paths.get("./"+url + "/" + cateId+ "/"+ id + "/");	
			 // Store in this path: the path is under the static file in the project directory: (Note: this file may need to be created by yourself)
			 // The reason for putting it under static is that it stores static file resources, that is, you can enter the local server address through the browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);
		
		return 0;
	}

	@Override
	public int delefile(long cateId, long id, String urlImg) throws IOException {
		if(urlImg.equals("images/avatars/userDefault.png"))
				return 0;
		String realPath = Paths.get("./"+ImageConstants.URL_IMAGE_PRODUCT + cateId +"/"+id+"/").toString();
		String imagePath = Paths.get("./"+urlImg).toString();
		
		return FileUtil.deleteImgDetail(realPath, imagePath);
	}

	@Override
	public int uploadFileUserDefault(MultipartFile file, String url, String fileName) throws IOException {
		if(file.isEmpty()) 
			return -1;
			// Get the file name, including the suffix			
		Path uploadPath = Paths.get("./"+url+ "/");	
		
		
			 // Store in this path: the path is under the static file in the project directory: (Note: this file may need to be created by yourself)
			 // The reason for putting it under static is that it stores static file resources, that is, you can enter the local server address through the browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);
		
		return 0;
	}

}
