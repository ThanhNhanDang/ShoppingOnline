package com.java.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.java.dto.ProductsDto;
import com.java.entity.Products;

public interface ProductService extends BaseService<Integer, ProductsDto, Long>{
	Page<ProductsDto> getProductsByCategoryId (long categoryId, Pageable pageable);
	Page<ProductsDto> getAllProducts( Pageable pageable);
	Page<ProductsDto> getProductsSearch(Pageable pageable, String key, long cateId);
	
	boolean updateInSockAndUnitSold(long productId, int quan) throws Exception;
	boolean updateReviewOfProduct(long id, long numberStar, int average) throws Exception;
	boolean isExits(long id);
	
	int edit(ProductsDto dto) throws Exception;
	int edit(String dto, MultipartFile file) throws Exception;
	
	List<ProductsDto> deleteAllBySelect(List<ProductsDto> dtos) throws Exception;
	List<ProductsDto> searchAdmin(String key);

	Products saveReturn (String dto, MultipartFile file) throws Exception; 
	Products isExits(long id, long cateId);
}
