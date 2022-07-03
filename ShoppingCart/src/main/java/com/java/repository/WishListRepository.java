package com.java.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.WishListDto;
import com.java.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long>{
	@Query("SELECT wishList FROM WishList wishList WHERE wishList.productId = :productId and wishList.userId = :userId")
	Optional<WishList> getWishListByProductIdAndUserId (@Param("userId") long userId, @Param("productId") long productId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM WishList wishList WHERE wishList.id = :id and wishList.userId = :userId")
	void deleteByUserId(@Param("id")long id, @Param("userId")long userId);
	
	@Query("select new com.java.dto.WishListDto(wishList.id, wishList.productId, wishList.pricePerItem, wishList.quantity, wishList.userId, p.name,p.urlImg) from WishList wishList, Products p WHERE wishList.userId = :userId and wishList.products=p")
	List<WishListDto> getAllByUser(@Param("userId")long userId);
	
	@Query("select new com.java.dto.WishListDto(wishList.id, wishList.productId, wishList.pricePerItem, wishList.quantity, wishList.userId, p.name,p.urlImg) from WishList wishList, Products p WHERE wishList.userId = :userId and wishList.id = :wishlistId and wishList.products=p")
	WishListDto getItem(@Param("userId")long userId, @Param("wishlistId") long wishlistId);
}
