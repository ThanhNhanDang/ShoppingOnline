package com.java.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.dto.ProductsDto;
import com.java.entity.Products;

public interface ProductService extends BaseService<Integer, ProductsDto, Long>{
	Page<ProductsDto> getProductsByCategoryId (long categoryId, Pageable pageable);
	Page<ProductsDto> getAllProducts( Pageable pageable);
	boolean updateInSockAndUnitSold(long productId, int quan) throws Exception;
	boolean updateReviewOfProduct(long id, long numberStar) throws Exception;
	int editMyAccout(ProductsDto dto) throws Exception;
	List<ProductsDto> deleteAllBySelect(List<ProductsDto> dtos) throws Exception;
	Products saveReturn (ProductsDto dto) throws Exception; 
	Page<ProductsDto> getProductsSearch(Pageable pageable, String key, long cateId);
	List<ProductsDto> searchAdmin(String key);
	Products isExits(long id, long cateId);
	boolean isExits(long id);
	
}
