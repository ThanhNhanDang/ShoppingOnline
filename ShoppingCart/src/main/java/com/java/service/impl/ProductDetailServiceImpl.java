package com.java.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.contants.ImageConstants;
import com.java.dto.ProductDetailDto;
import com.java.entity.ProductDetail;
import com.java.repository.ProductDetailRepository;
import com.java.service.FileService;
import com.java.service.ProductDetailService;
import com.java.service.ProductService;

@Service
@Scope("prototype")
public class ProductDetailServiceImpl implements ProductDetailService {
	private ProductDetailRepository repo;
	private ProductService productService;
	private FileService fileService;

	public ProductDetailServiceImpl(ProductDetailRepository repo, ProductService productService,
			FileService fileService) {
		this.repo = repo;
		this.productService = productService;
		this.fileService = fileService;
	}

	private ProductDetailDto jsonMapper(String dto) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(dto, ProductDetailDto.class);
	}

	@Override
	public List<ProductDetailDto> getProductDetailByProduct(long productId, long cateId) throws Exception {
		if (productService.isExits(productId, cateId) == null)
			throw new Exception("Not found");
		List<ProductDetailDto> dtos = repo.getProductDetailByProduct(productId);
		return dtos;
	}

	@Transactional
	@Modifying
	@Override
	public ProductDetailDto addProductDetail(String dto, MultipartFile file) throws IOException {
		ProductDetailDto dtoJson = this.jsonMapper(dto);
		ProductDetail entity = new ProductDetail();
		entity.setProductId(dtoJson.getProductId());
		entity.setUrlImg(UUID.randomUUID() + ".png");
		this.fileService.store(file, entity.getUrlImg(), 4, entity.getProductId());
		dtoJson.setId(repo.save(entity).getId());
		return dtoJson;
	}

	@Transactional
	@Modifying
	@Override
	public void updateProductDetail(String dto, MultipartFile file) throws Exception {
		ProductDetailDto dtoJson = this.jsonMapper(dto);
		if (!repo.existsById(dtoJson.getId())) {
			throw new Exception("Not found");
		}
		ProductDetail entity = repo.findById(dtoJson.getId()).get();
		this.fileService.delefile(entity.getProductId(), ImageConstants.URL_IMAGE_PRODUCT, entity.getUrlImg());
		entity.setUrlImg(UUID.randomUUID() + ".png");
		this.fileService.store(file, entity.getUrlImg(), 4, entity.getProductId());
		repo.save(entity);
	}

	@Transactional
	@Modifying
	@Override
	public void updateAllProductDetail(String listDto, List<MultipartFile> files) throws Exception {
		List<ProductDetailDto> dtoJson = new ArrayList<ProductDetailDto>();
		List<ProductDetail> entities = new ArrayList<ProductDetail>();
		ObjectMapper objectMapper = new ObjectMapper();
		dtoJson = objectMapper.readValue(listDto, new TypeReference<List<ProductDetailDto>>() {
		});
		for (int i = 0; i < dtoJson.size(); i++) {
			if (!repo.existsById(dtoJson.get(i).getId())) {
				throw new Exception("Not found");
			}
			ProductDetail entity = repo.findById(dtoJson.get(i).getId()).get();
			this.fileService.delefile(entity.getProductId(), ImageConstants.URL_IMAGE_PRODUCT, entity.getUrlImg());
			entity.setUrlImg(UUID.randomUUID() + ".png");
			this.fileService.store(files.get(i), entity.getUrlImg(), 4, entity.getProductId());
			entities.add(entity);
		}

		repo.saveAll(entities);

	}

	@Transactional
	@Modifying
	@Override
	public void delete(long id) throws Exception {
		if (!repo.existsById(id)) {
			throw new Exception("Not found");
		}
		ProductDetail entity = repo.findById(id).get();
		this.fileService.delefile(entity.getProductId(), ImageConstants.URL_IMAGE_PRODUCT, entity.getUrlImg());
		repo.deleteById(id);

	}

	@Transactional
	@Modifying
	@Override
	public List<ProductDetailDto> addAllProductDetail(String listDto, List<MultipartFile> files) throws IOException {
		List<ProductDetailDto> dtoJson = new ArrayList<ProductDetailDto>();
		List<ProductDetail> entities = new ArrayList<ProductDetail>();

		ObjectMapper objectMapper = new ObjectMapper();
		dtoJson = objectMapper.readValue(listDto, new TypeReference<List<ProductDetailDto>>() {
		});
		for (int i = 0; i < dtoJson.size(); i++) {
			ProductDetail entity = new ProductDetail();
			UUID uuid = UUID.randomUUID();
			entity.setProductId(dtoJson.get(0).getProductId());
			entity.setUrlImg(uuid + ".png");
			this.fileService.store(files.get(i), entity.getUrlImg(), 4, entity.getProductId());
			entities.add(entity);
		}

		entities = repo.saveAll(entities);
		for (int i = 0; i < dtoJson.size(); i++) {
			dtoJson.get(i).setId(entities.get(i).getId());
		}
		return dtoJson;
	}

	@Transactional
	@Modifying
	@Override
	public void deleteAllByProductIdAndFile(long productId) throws Exception {
		List<ProductDetail> entities = repo.findAllByProductId(productId);
		for (ProductDetail entity : entities) {
			this.fileService.delefile(productId, ImageConstants.URL_IMAGE_PRODUCT, entity.getUrlImg());
		}
		repo.deleteAllByProductId(productId);
	}

	@Transactional
	@Modifying
	@Override
	public void deleteAllByProductId(long productId) {
		repo.deleteAllByProductId(productId);
	}

}
