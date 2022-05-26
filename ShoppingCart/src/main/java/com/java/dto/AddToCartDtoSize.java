package com.java.dto;

import java.util.List;

public class AddToCartDtoSize {
	private List<AddToCartDto> addToCartDtos;
	private long size;
	public AddToCartDtoSize(List<AddToCartDto> addToCartDtos, long size) {
		this.addToCartDtos = addToCartDtos;
		this.size = size;
	}
	public List<AddToCartDto> getAddToCartDtos() {
		return addToCartDtos;
	}
	public long getSize() {
		return size;
	}
	
	
}
