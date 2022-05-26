package com.java.service;

import java.util.List;

import com.java.dto.AddToCartDto;
import com.java.dto.CheckOutCartDto;
import com.java.entity.CheckOutCart;

public interface CartService {
	List<AddToCartDto> addCartByUserIdAndProductId(long userId, long productId, int quantity) throws Exception;
	void updateQtyByCartId(List<AddToCartDto> dtos) throws Exception;
	void updateItem(double price, long id, int quantity) throws Exception;
	List<AddToCartDto> getCartByUserId(long userId);
	AddToCartDto getCartById(long userId, long cartId);
	List<AddToCartDto> deleteCartByUserId(long id, long userId);
	List<AddToCartDto> deleteAllCartByUserId(long userId);
	boolean checkTotalPriceAgainstCart(double total, long userId);
	//CheckOutCart;
	List<CheckOutCartDto> getCheckOutCartByUserId(long userId);
	List<CheckOutCartDto> getAll();
	//CheckOutCart;
	void addProductsForCheckOutCart(List<CheckOutCart> entities) throws Exception;

	int getOrderId();
}
