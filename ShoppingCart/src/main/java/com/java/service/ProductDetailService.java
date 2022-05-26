package com.java.service;

import java.util.List;

import com.java.dto.ProductDetailDto;

public interface ProductDetailService {
	List<ProductDetailDto> getProductDetailByProduct(long productId, long cateId) throws Exception;
	ProductDetailDto addProductDetail(ProductDetailDto detailDto);
	void update(ProductDetailDto dto) throws Exception;
	void delete(long id) throws Exception;
}
