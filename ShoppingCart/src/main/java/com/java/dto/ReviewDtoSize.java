package com.java.dto;

import java.util.List;

public class ReviewDtoSize {
	private List<ReviewDto> dtos;
	private long size;
	public ReviewDtoSize(List<ReviewDto> dtos, long size) {
		this.dtos = dtos;
		this.size = size;
	}
	public List<ReviewDto> getDtos() {
		return dtos;
	}
	public void setDtos(List<ReviewDto> dtos) {
		this.dtos = dtos;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	
	
}
