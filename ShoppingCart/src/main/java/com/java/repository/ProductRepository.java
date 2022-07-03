package com.java.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.ProductsDto;
import com.java.entity.Products;


@Repository
public interface ProductRepository extends JpaRepository<Products, Long>{
	@Query("SELECT p FROM Products p ORDER BY id DESC")
	List<Products> findAllReverse (); 
	@Query("SELECT p FROM Products p Where p.id= :id and p.category_id= :cateId")
	Products existsByIdCateId(long id, long cateId);
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where CONCAT(products.name, ' ', products.description, ' ', c.name) LIKE %:key% and products.category=c  ORDER BY products.id DESC")
	List<ProductsDto> searchAdmin(@Param("key") String key);
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c WHERE products.category_id = :category_id and products.category = c ORDER BY products.id DESC ")
	Page<ProductsDto> getProductsByCategoryId (@Param("category_id") long categoryId, Pageable pageable);
	
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where products.category = c ORDER BY products.id DESC")
	Page<ProductsDto> getAllProducts(Pageable pageable);
	
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where CONCAT(products.name, ' ', products.description, ' ', c.name) LIKE %:key% and products.category=c")
	Page<ProductsDto> getAllProductsSearch(Pageable pageable, @Param("key") String key);
	
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where CONCAT(products.name, ' ', products.description, ' ', c.name) LIKE %:key% and products.category_id = :cateId and products.category=c ")
	Page<ProductsDto> getAllProductsSearchSortWithCateId(Pageable pageable,  @Param("key") String key, @Param("cateId")long cateId);

	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where products.category=c")
	Page<ProductsDto> getAllProductsSort(Pageable pageable);
	
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where products.category_id = :cateId and products.category=c")
	Page<ProductsDto> getAllProductsSortWithCateId(Pageable pageable, @Param("cateId")long cateId);
	
	@Query("select new com.java.dto.ProductsDto(products.id, products.category_id, products.name, products.price, products.added_on, products.exprideDate, products.inStock, products.unitSold, products.urlImg, products.description, c.name, products.totalReview, products.totalReview5Star) from Products products, Category c Where products.id= :id and products.category=c ")
	ProductsDto getProductById(@Param("id") long id);
	
}
