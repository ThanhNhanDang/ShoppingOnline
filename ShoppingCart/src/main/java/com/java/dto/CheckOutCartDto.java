package com.java.dto;

import java.time.Instant;


public class CheckOutCartDto {
	private long 	id;
	private long 	productId;
	private int 	quantity;
	private double	price;
	private Instant	orderDate;
	private long 	userId;
	private String 	orderId;
	private String  paymentType;
	private String  deliveryAddress;
	private String 	userName;
	private String 	productName;
	private double  pricePerItem;
	
	
	

	public CheckOutCartDto() {}

	public CheckOutCartDto(long id, long productId, int quantity, double price, Instant orderDate, long userId,
			String orderId, String paymentType, String deliveryAddress, String 	userName, String 	productName, double  pricePerItem) {
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.orderDate = orderDate;
		this.userId = userId;
		this.orderId = orderId;
		this.paymentType = paymentType;
		this.deliveryAddress = deliveryAddress;
		this.userName = userName;
		this.productName = productName;
		this.pricePerItem = pricePerItem;
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

	public Instant getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPricePerItem() {
		return pricePerItem;
	}

	public void setPricePerItem(double pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	
}
