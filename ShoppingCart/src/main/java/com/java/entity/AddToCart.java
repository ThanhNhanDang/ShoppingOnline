package com.java.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class AddToCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private long	product_id;
	private int 	quantity;
	private double	price;
	private long 	user_id;
	private Instant	added_date;
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User    user;
	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private Products products;
	public AddToCart() {}

	public AddToCart(long id, long product_id, int quantity, double price, Instant added_date,long user_id) {
		this.id = id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
		this.added_date = added_date;
		this.user_id = user_id;
	}

	public AddToCart(long product_id, int quantity, double price, Instant added_date, long user_id) {
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
		this.added_date = added_date;
		this.user_id = user_id;
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
	public Products getProducts() {
		return products;
	}
	
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	public User getUser() {
		return user;
	}
	
}
