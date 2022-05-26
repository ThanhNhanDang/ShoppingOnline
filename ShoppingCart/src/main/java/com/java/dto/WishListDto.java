package com.java.dto;

public class WishListDto {
	private long id;
	private long productId;
	private double pricePerItem;
    private int	  quantity;
    private long userId;
    private String productName;
    private String urlImg;
    
    
    
	public WishListDto(long productId, double pricePerItem, int quantity, long userId, String productName,
			String urlImg) {
		this.productId = productId;
		this.pricePerItem = pricePerItem;
		this.quantity = quantity;
		this.userId = userId;
		this.productName = productName;
		this.urlImg = urlImg;
	}
	public WishListDto(long id, long productId, double pricePerItem, int quantity, long userId, String productName,
			String urlImg) {
		this.id = id;
		this.productId = productId;
		this.pricePerItem = pricePerItem;
		this.quantity = quantity;
		this.userId = userId;
		this.productName = productName;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
    
   
	
}
