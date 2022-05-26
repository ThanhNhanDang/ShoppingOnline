package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.ReviewDto;
import com.java.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	@Query("select new com.java.dto.ReviewDto(review.id, review.userId, review.productId, review.message, review.reviewDate, u.image_url,  u.name, review.ratting) from Review review, User u WHERE review.productId = :productId and review.user=u")
	List<ReviewDto> findAllReviewByProduct(@Param("productId") long productId);
	
	@Query("select new com.java.dto.ReviewDto(review.id, review.userId, review.productId, review.message, review.reviewDate, u.image_url,  u.name, u.email, p.urlImg, p.name, review.ratting) from Review review, User u, Products p WHERE review.userId = :userId and review.user=u and review.products = p")
	List<ReviewDto> findAllReviewByUser(@Param("userId") long userId);
	
	@Query("delete from Review review WHERE review.userId = :userId and review.productId = :productId")
	List<ReviewDto> deleteReviewByUser(@Param("userId") long userId, @Param("productId") long productId);
}
