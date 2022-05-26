package com.java.service;

import java.util.List;

import com.java.dto.WishListDto;

public interface WishListService {
	int addByUserAndProduct(long userId, long productId, int quantity) throws Exception;
	List<WishListDto> delete(long id, long userId);
	List<WishListDto> getAllByUser(long userId);
	WishListDto getItem(long userId, long wishlistId) throws Exception;
}
