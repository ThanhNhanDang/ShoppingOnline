package com.java.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.dto.ProductDetailDto;
import com.java.entity.ProductDetail;
import com.java.repository.ProductDetailRepository;
import com.java.service.ProductDetailService;
import com.java.service.ProductService;

@Service
@Scope("prototype")
public class ProductDetailServiceImpl implements ProductDetailService{
	private ProductDetailRepository repo;
	private ProductService productService;
	public ProductDetailServiceImpl(ProductDetailRepository repo, ProductService productService) {
		this.repo = repo;
		this.productService = productService;
	}

	@Override
	public List<ProductDetailDto> getProductDetailByProduct(long productId, long cateId) throws Exception {
		if(productService.isExits(productId, cateId)==null)
			throw new Exception("Not found");
		List<ProductDetailDto>dtos = repo.getProductDetailByProduct(productId);
		return dtos;
	}
	@Transactional
	@Modifying
	@Override
	public ProductDetailDto addProductDetail(ProductDetailDto detailDto) {
		
		ProductDetail entity = new ProductDetail();
		entity.setProductId(detailDto.getProductId());
		entity.setUrlImg(detailDto.getUrlImg());
		
		detailDto.setId(repo.save(entity).getId());
		return detailDto;
	}
	
	@Transactional
	@Modifying
	@Override
	public void update(ProductDetailDto dto) throws Exception{
		if(!repo.existsById(dto.getId())) {
			throw new Exception("Not found");
		}
		ProductDetail entity = repo.findById(dto.getId()).get();
		entity.setUrlImg(dto.getUrlImg());
		repo.save(entity);
	}

	@Override
	public void delete(long id) throws Exception {
		if(!repo.existsById(id)) {
			throw new Exception("Not found");
		}
		repo.deleteById(id);
		
	}

	
}
