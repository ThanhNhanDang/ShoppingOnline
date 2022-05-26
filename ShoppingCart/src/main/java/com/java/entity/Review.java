package com.java.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long	id;
	private long 	userId;
	private long 	productId;
	private String 	message;
	private Instant reviewDate;
	private int 	ratting;
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User	user;
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Products products;
	
	public Review() {}
	
	public Review(long id, long userId, long productId, String message, Instant reviewDate, int ratting) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.message = message;
		this.reviewDate = reviewDate;
		this.ratting = ratting;
	}


	public Review(long userId, long productId, String message, Instant reviewDate, int 	ratting) {
		
		this.userId = userId;
		this.productId = productId;
		this.message = message;
		this.reviewDate = reviewDate;
		this.ratting = ratting;
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

	public Instant getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Instant reviewDate) {
		this.reviewDate = reviewDate;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}
	
	
}
