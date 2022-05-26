package com.java.service;

import com.java.dto.ReviewDto;
import com.java.dto.ReviewDtoSize;

public interface ReviewService{
	ReviewDto save (ReviewDto reviewDto) throws Exception;
	ReviewDtoSize findAllReviewByUser(long userId);
	
	ReviewDtoSize findAllReviewByProduct(long productId);
	
	int deleteReviewByUserAndProduct(long userId, long productId, long reviewId);
	
	
	
}
