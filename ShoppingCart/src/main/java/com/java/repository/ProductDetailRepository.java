package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.ProductDetailDto;
import com.java.entity.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>{
	@Query("select new com.java.dto.ProductDetailDto(detail.id,detail.productId,detail.urlImg) from ProductDetail detail WHERE detail.productId = :productId ORDER BY id DESC")
	List<ProductDetailDto> getProductDetailByProduct (@Param("productId") long productId);
}