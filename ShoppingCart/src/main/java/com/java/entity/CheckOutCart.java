package com.java.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CheckOutCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private long 	productId;
	private int 	quantity;
	private double	price;
	private Instant	orderDate;
	private long 	userId;
	
	private String 	orderId, paymentType, deliveryAddress;
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Products products;

	public CheckOutCart() {}

	public CheckOutCart(long id, long productId, int quantity, double price, Instant orderDate, long userId,
			String orderId, String paymentType, String deliveryAddress) {
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.orderDate = orderDate;
		this.userId = userId;
		this.orderId = orderId;
		this.setPaymentType(paymentType);
		this.deliveryAddress = deliveryAddress;
		
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
