package com.java.dto;

public class ProductDetailDto {
	private long id;
	private long productId;
	private String urlImg;
	private long fileId;
	public ProductDetailDto() {
		// TODO Auto-generated constructor stub
	}
	public ProductDetailDto(long id, long productId, String urlImg, long fileId) {
		this.id = id;
		this.productId = productId;
		this.urlImg = urlImg;
		this.fileId = fileId;
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
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
}
