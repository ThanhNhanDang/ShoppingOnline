package com.java.dto;

public class ProductDetailDto {
	private long id;
	private long productId;
	private String urlImg;
	public ProductDetailDto() {
		// TODO Auto-generated constructor stub
	}
	public ProductDetailDto(long id, long productId, String urlImg) {
		this.id = id;
		this.productId = productId;
		this.urlImg = urlImg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
}
