package com.java.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.java.contants.ImageConstants;
import com.java.entity.FileDB;
import com.java.repository.FileDBRepository;
import com.java.service.FileService;
import com.java.utils.FileUtil;
import com.java.utils.ImageUtil;

@Service
@Scope("prototype")
public class FileServiceImpl implements FileService {

	private FileDBRepository fileDBRepository;

	public FileServiceImpl(FileDBRepository fileDBRepository) {
		super();
		this.fileDBRepository = fileDBRepository;
	}

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
	public int delefile(long cateId, long id) throws IOException {
		String realPath = Paths.get("./static/" + ImageConstants.URL_IMAGE_PRODUCT + cateId + "/" + id + "/")
				.toString();
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
	public int delefileUser(long id) throws IOException {
		String realPath = Paths.get("./static/" + ImageConstants.URL_IMAGE_AVATAR + "/" + id + "/").toString();
		return FileUtil.deleteFile(realPath);
	}

	@Override
	public void updateImageDetail(MultipartFile file, long cateId, long id) throws IOException {
		if (file.isEmpty())
			throw new IOException("File is empty");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get("./static/" + ImageConstants.URL_IMAGE_PRODUCT + "/" + cateId + "/" + id + "/");
		FileUtil.fileUpdate(file.getInputStream(), uploadPath, fileName);

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
	public int delefile(long cateId, long id, String urlImg) throws IOException {
		if (urlImg.equals("images/avatars/userDefault.png"))
			return 0;
		String realPath = Paths.get("./static/" + ImageConstants.URL_IMAGE_PRODUCT + cateId + "/" + id + "/")
				.toString();
		String imagePath = Paths.get("./static/" + urlImg).toString();

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

	@Transactional
	@Modifying
	@Override
	public FileDB store(MultipartFile file, String fileName) throws IOException {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		FileDB fileDB = fileDBRepository.findByName(fileName);
		if (fileDB != null)
			fileDB.setData(ImageUtil.compressImage(file.getBytes()));
		else
			fileDB = new FileDB(fileName, file.getContentType(), ImageUtil.compressImage(file.getBytes()));
		return fileDBRepository.save(fileDB);
	}

	@Transactional
	@Modifying
	@Override
	public FileDB store(MultipartFile file, long id) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = null;
		if (id == 1 || id == 2)// userDefault
			fileDB = new FileDB(fileName, file.getContentType(), ImageUtil.compressImage(file.getBytes()));
		else {
			fileDB = fileDBRepository.findById(id).get();
			fileDB.setName(fileName);
			fileDB.setType(file.getContentType());
			fileDB.setData(ImageUtil.compressImage(file.getBytes()));
		}
		return fileDBRepository.save(fileDB);
	}

	public List<FileDB> getAllFileDB() {
		return fileDBRepository.findAll();
	}

	@Override
	public Optional<FileDB> findById(long id) {
		final Optional<FileDB> dbImage = fileDBRepository.findById(id);
		return dbImage;
	}

	@Override
	public void deleteFileDB(long id) {
		if(id == 1 || id == 2)
			return;
		fileDBRepository.deleteById(id);
	}

	@Override
	public FileDB store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = new FileDB(fileName, file.getContentType(), ImageUtil.compressImage(file.getBytes()));
		return fileDBRepository.save(fileDB);
	}

	@Override
	public FileDB storeUpdate(MultipartFile file, long fileId) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = null;
		if (fileId == 1 || fileId == 2) {
			fileDB = new FileDB(fileName, file.getContentType(), ImageUtil.compressImage(file.getBytes()));
		} else {
			fileDB = fileDBRepository.findById(fileId).get();
			if (fileDB == null)
				return null;

			else {
				fileDB.setName(fileName);
				fileDB.setType(file.getContentType());
				fileDB.setData(ImageUtil.compressImage(file.getBytes()));
			}
		}
		return fileDBRepository.save(fileDB);
	}

}
