package com.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WishList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long productId;
	private double pricePerItem;
    private int	  quantity;
    private long userId;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Products products;
    
    public WishList() {}
   
	public WishList(long id, long productId, double pricePerItem, int quantity, long userId) {
		
		this.id = id;
		this.productId = productId;
		this.pricePerItem = pricePerItem;
		this.quantity = quantity;
		this.userId = userId;
	}
	public WishList(long productId, double pricePerItem, int quantity, long userId) {
		
		this.productId = productId;
		this.pricePerItem = pricePerItem;
		this.quantity = quantity;
		this.userId = userId;
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
	public double getPricePerItem() {
		return pricePerItem;
	}
	public void setPricePerItem(double pricePerItem) {
		this.pricePerItem = pricePerItem;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
    
	
    
}
