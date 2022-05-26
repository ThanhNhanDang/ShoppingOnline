package com.java.dto;

import java.time.Instant;

public class AddToCartDto {
	private long 	id;
	private long	product_id;
	private int 	quantity;
	private double	price;
	private Instant	added_date;
	private long 	user_id;
	private String 	userName;
	private String 	productName;
	private String 	urlImg;
	private double  pricePerItem;
	public AddToCartDto() {}

	public AddToCartDto(long id, long product_id, int quantity, double price, Instant added_date, long user_id, String userName, String productName, double pricePerItem, String urlImg) {
		this.id = id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
		this.added_date = added_date;
		this.user_id = user_id;
		this.userName = userName;
		this.productName = productName;
		this.pricePerItem = pricePerItem;
		this.urlImg = urlImg;
	}

	public AddToCartDto(long id, long product_id, int quantity, double price, Instant added_date, long user_id, String urlImg) {
		this.id = id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
		this.added_date = added_date;
		this.user_id = user_id;
		this.urlImg = urlImg;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Instant getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Instant added_date) {
		this.added_date = added_date;
	}
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductName() {
		return productName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public double getPricePerItem() {
		return pricePerItem;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
}
