package com.java.dto;

import java.util.List;

public class WishListDtoSize {
	private List<WishListDto> dtos;
	private int size;
	public WishListDtoSize(List<WishListDto> dtos, int size) {
		this.dtos = dtos;
		this.size = size;
	}
	public List<WishListDto> getDtos() {
		return dtos;
	}
	public void setDtos(List<WishListDto> dtos) {
		this.dtos = dtos;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
