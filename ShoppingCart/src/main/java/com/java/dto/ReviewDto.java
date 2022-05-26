package com.java.dto;

import java.time.Instant;

public class ReviewDto {
	private long	id;
	private long 	userId;
	private long 	productId;
	private String 	message;
	private Instant reviewDate;
	private String 	reviewDateCustom;
	private String 	urlImg;
	private String 	userName;
	private String 	email;
	private String  urlImgProduct;
	private String 	productName;
	private int 	ratting;



	public ReviewDto(long id, long userId, long productId, String message, Instant reviewDate, String urlImg, String email, 
			String userName, String urlImgProduct, String productName, int ratting) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.message = message;
		this.reviewDate = reviewDate;
		this.urlImg = urlImg;
		this.userName = userName;
		this.email = email;
		this.urlImgProduct = urlImgProduct;
		this.productName = productName;
		this.ratting = ratting;
	}



	public ReviewDto() {}
	
	
	
	public ReviewDto(long id, long userId, long productId, String message, Instant reviewDate, String urlImg,
			String userName, int ratting) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.message = message;
		this.reviewDate = reviewDate;
		this.urlImg = urlImg;
		this.userName = userName;
		this.ratting = ratting;
	}



	public ReviewDto(long userId, long productId, String message, Instant reviewDate, String urlImg, String userName, int ratting) {
		this.userId = userId;
		this.productId = productId;
		this.message = message;
		this.reviewDate = reviewDate;
		this.urlImg = urlImg;
		this.userName = userName;
		this.ratting = ratting;
	}



	public Instant getReviewDate() {
		return reviewDate;
	}


	public void setReviewDate(Instant reviewDate) {
		this.reviewDate = reviewDate;
	}


	public String getUrlImg() {
		return urlImg;
	}


	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrlImgProduct() {
		return urlImgProduct;
	}



	public void setUrlImgProduct(String urlImgProduct) {
		this.urlImgProduct = urlImgProduct;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getReviewDateCustom() {
		return reviewDateCustom;
	}



	public void setReviewDateCustom(String reviewDateCustom) {
		this.reviewDateCustom = reviewDateCustom;
	}



	public int getRatting() {
		return ratting;
	}



	public void setRatting(int ratting) {
		this.ratting = ratting;
	}
}
