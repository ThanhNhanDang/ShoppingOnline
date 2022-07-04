package com.java.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.java.dto.ProductDetailDto;

public interface ProductDetailService {
	List<ProductDetailDto> getProductDetailByProduct(long productId, long cateId) throws Exception;
	List<ProductDetailDto> addAllProductDetail(String dto, List<MultipartFile> files) throws JsonMappingException, JsonProcessingException, IOException;
	ProductDetailDto addProductDetail(String dto, MultipartFile file) throws IOException;
	void updateProductDetail(String dto, MultipartFile file) throws Exception;
	void updateAllProductDetail(String listDto, List<MultipartFile> files) throws Exception;
	void delete(long id) throws Exception;
	void deleteAllByProductIdAndFile(long productId) throws Exception;
	void deleteAllByProductId(long productId);
}
