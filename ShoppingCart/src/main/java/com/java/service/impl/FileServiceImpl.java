
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
public class FileServiceImpl implements FileService {

	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@Override
	public int uploadFileProduct(MultipartFile file, String url, long cateId, long id) throws IOException {
		if (file.isEmpty())
			return -1;
		// Get the file name, including the suffix
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path staticPath = Paths.get("static");
		Path imagePath = Paths.get(url + "/" + cateId + "/" + id);
		Path uploadPath = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath);
		// Store in this path: the path is under the static file in the project
		// directory: (Note: this file may need to be created by yourself)
		// The reason for putting it under static is that it stores static file
		// resources, that is, you can enter the local server address through the
		// browser, and you can access it when adding the file name
		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);

		return 0;
	}

	@Override
	public int deleFolder(long id, String location) throws IOException {
		String realPath = Paths.get("." + location + "/" + id + "/").toString();
		return FileUtil.deleteFile(realPath);
	}

	@Override
	public int uploadFileUser(MultipartFile file, String url, long id) throws IOException {
		if (file.isEmpty())
			return -1;
		// Get the file name, including the suffix
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get("./static/" + url + "/" + id + "/");

		// Store in this path: the path is under the static file in the project
		// directory: (Note: this file may need to be created by yourself)
		// The reason for putting it under static is that it stores static file
		// resources, that is, you can enter the local server address through the
		// browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);

		return 0;
	}

	@Override
	public void updateImageDetail(MultipartFile file, long cateId, long id) throws IOException {
		if (file.isEmpty())
			throw new IOException("File is empty");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get("./static/" + ImageConstants.URL_IMAGE_PRODUCT + "/" + cateId + "/" + id + "/");
		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);

	}

	@Override
	public int uploadFileProduct(MultipartFile file, String url, String cateId, String id) throws IOException {
		if (file.isEmpty())
			return -1;
		// Get the file name, including the suffix
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get("./static/" + url + "/" + cateId + "/" + id + "/");
		// Store in this path: the path is under the static file in the project
		// directory: (Note: this file may need to be created by yourself)
		// The reason for putting it under static is that it stores static file
		// resources, that is, you can enter the local server address through the
		// browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);

		return 0;
	}

	@Override
	public int delefile(long id, String location, String urlImg) throws IOException {
		if (urlImg.equals("userDefault.png")) {
			return 0;
		}
		String realPath;
		String imagePath;
		if (location.equals(ImageConstants.URL_IMAGE_AVATAR)) {
			realPath = Paths.get("." + location + "/" + id + "/").toString();
			imagePath = Paths.get("./static/images/" + urlImg).toString();
		} else {
			realPath = Paths.get("." + location + "/" + id + "/").toString();
			imagePath = Paths.get("." + location + "/" + id + "/" + urlImg).toString();
		}
		return FileUtil.deleteImgDetail(realPath, imagePath);
	}

	@Override
	public int uploadFileUserDefault(MultipartFile file, String url, String fileName) throws IOException {
		if (file.isEmpty())
			return -1;
		// Get the file name, including the suffix
		Path uploadPath = Paths.get("./static/" + url + "/");

		// Store in this path: the path is under the static file in the project
		// directory: (Note: this file may need to be created by yourself)
		// The reason for putting it under static is that it stores static file
		// resources, that is, you can enter the local server address through the
		// browser, and you can access it when adding the file name

		FileUtil.fileupload(file.getInputStream(), uploadPath, fileName);

		return 0;
	}

	@Override
	public void store(MultipartFile file, String fileName, int type, long id) throws IOException {
		Path dir = null;
		switch (type) {
		case 1:
		case 2:
			dir = Paths.get("." + ImageConstants.URL_IMAGE_BASE);
			break;
		case 3:
			dir = Paths.get("." + ImageConstants.URL_IMAGE_AVATAR + "/" + id);
			break;
		case 4:
			dir = Paths.get("." + ImageConstants.URL_IMAGE_PRODUCT + "/" + id);
			break;
		default:
			break;
		}

		FileUtil.fileupload(file.getInputStream(), dir, fileName);

	}

	@Override
	public void store(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub

	}

}
