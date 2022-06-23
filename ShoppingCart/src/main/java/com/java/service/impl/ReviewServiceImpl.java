package com.java.service.impl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.dto.ReviewDto;
import com.java.dto.ReviewDtoSize;
import com.java.entity.Review;
import com.java.repository.ReviewRepository;
import com.java.service.ProductService;
import com.java.service.ReviewService;

@Service
@Scope("prototype")
public class ReviewServiceImpl implements ReviewService {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
			.withZone(ZoneId.systemDefault());
	private ReviewRepository reviewRepo;
	private ProductService productService;

	public ReviewServiceImpl(ReviewRepository reviewRepo, ProductService productService) {
		this.reviewRepo = reviewRepo;
		this.productService = productService;
	}

	@Modifying
	@Transactional
	@Override
	public ReviewDto save(ReviewDto reviewDto) throws Exception {
		if (reviewDto.getRatting() > 5 || reviewDto.getRatting() < 1) {
			throw new Exception("Can't review. Invalid rating");
		}
		Review entity = new Review(reviewDto.getUserId(), reviewDto.getProductId(), reviewDto.getMessage(),
				reviewDto.getReviewDate(), reviewDto.getRatting());
		float temp = 0;
		int average = 0;
		List<ReviewDto> dtos = null;
		if (reviewRepo.save(entity) == null)
			return null;
		dtos = reviewRepo.findAllReviewByProduct(entity.getProductId());
		for (ReviewDto dto : dtos) {
			average += dto.getRatting();
		}
		// lấy phần dư bằng cách lấy số thực trừ cho phần nguyên
		temp = (float)average/dtos.size() - average/dtos.size();
		// Sau đó kiểm tra nếu phần dư lớn hơn hoặc bằng 0.5 thì có nghĩa là làm tròn lên
		average = (int) average/dtos.size() + ((temp < 0.5) ? 0 : 1);
		productService.updateReviewOfProduct(entity.getProductId(), entity.getRatting(), average);
		
		reviewDto.setReviewDateCustom(formatter.format(entity.getReviewDate()));
		return reviewDto;	
	}

	@Override
	public ReviewDtoSize findAllReviewByUser(long userId) {
		List<ReviewDto> dtos = reviewRepo.findAllReviewByUser(userId);
		if (dtos.isEmpty())
			return null;
		List<ReviewDto> dtos2 = new ArrayList<ReviewDto>();
		for (int i = dtos.size() - 1; i >= 0; i--) {
			ReviewDto dto = dtos.get(i);
			dtos2.add(dto);
		}
		ReviewDtoSize dtoSize = new ReviewDtoSize(dtos2, dtos2.size());
		return dtoSize;
	}

	@Override
	public ReviewDtoSize findAllReviewByProduct(long productId) {

		List<ReviewDto> dtos = reviewRepo.findAllReviewByProduct(productId);
		if (dtos.isEmpty())
			return null;
		List<ReviewDto> dtos2 = new ArrayList<ReviewDto>();
		for (int i = dtos.size() - 1; i >= 0; i--) {
			ReviewDto dto = dtos.get(i);
			dto.setReviewDateCustom(formatter.format(dto.getReviewDate()));
			dtos2.add(dto);
		}
		ReviewDtoSize dtoSize = new ReviewDtoSize(dtos2, dtos2.size());
		return dtoSize;
	}

	@Modifying
	@Transactional
	@Override
	public int deleteReviewByUserAndProduct(long userId, long productId, long reviewId) {
		if (!reviewRepo.existsById(reviewId))
			return -1;

		reviewRepo.deleteReviewByUser(userId, productId);
		return 0;
	}

}
