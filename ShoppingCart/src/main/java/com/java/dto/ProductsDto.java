package com.java.dto;

import java.time.Instant;

public class ProductsDto {
	private long 	id;
	private long 	category_id;
	private String 	categoryName;
	private String	name;
	private double	price;
	private Instant added_on;
	private boolean checkselect;
	private Instant exprideDate;
	private String	description;
	private String  exDate;
	private long 	inStock;
	private long 	unitSold;
	private String	urlImg;
	private long 	totalReview;
	private long 	totalReview5Star;
	public ProductsDto() {}


	public ProductsDto(long id, long category_id, String name, double price, Instant added_on, Instant exprideDate, long inStock, long unitSold, String	urlImg, String description, String categoryName, long totalReview, long totalReview5Star) {
		this.id = id;
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.added_on = added_on;
		this.exprideDate = exprideDate;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.description = description;
		this.categoryName = categoryName;
		this.totalReview = totalReview;
		this.totalReview5Star = totalReview5Star;
	}


	public ProductsDto(long id, long category_id, String name, double price, String exDate, long inStock, long unitSold, String urlImg,  String description, boolean checkselect, long totalReview, long totalReview5Star) {
		this.id = id;
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.exDate = exDate;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.description = description;
		this.checkselect = checkselect;
		this.totalReview = totalReview;
		this.totalReview = totalReview5Star;
	}
	
	public ProductsDto(long id, long category_id, String name, double price, String exDate, long inStock, Instant added_on, long unitSold, String	urlImg, String description , long totalReview, long totalReview5Star) {
		this.id = id;
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.added_on = added_on;
		this.exDate = exDate;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.description = description;
		this.totalReview = totalReview;
		this.totalReview5Star = totalReview5Star;

	}
	
	
	public ProductsDto(long category_id, String name, double price, Instant added_on, Instant exprideDate, long inStock, long unitSold, String	urlImg, long totalReview, long totalReview5Star) {
		
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.added_on = added_on;
		this.exprideDate = exprideDate;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.totalReview = totalReview;
		this.totalReview5Star = totalReview5Star;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Instant isAdded_on() {
		return added_on;
	}

	public void setAdded_on(Instant added_on) {
		this.added_on = added_on;
	}


	public String getUrlImg() {
		return urlImg;
	}


	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}


	public Instant getExprideDate() {
		return exprideDate;
	}


	public void setExprideDate(Instant exprideDate) {
		this.exprideDate = exprideDate;
	}




	public long getInStock() {
		return inStock;
	}




	public void setInStock(long inStock) {
		this.inStock = inStock;
	}




	public long getUnitSold() {
		return unitSold;
	}




	public void setUnitSold(long unitSold) {
		this.unitSold = unitSold;
	}


	public String getExDate() {
		return exDate;
	}


	public void setExDate(String exDate) {
		this.exDate = exDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isCheckselect() {
		return checkselect;
	}


	public void setCheckselect(boolean checkselect) {
		this.checkselect = checkselect;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public long getTotalReview() {
		return totalReview;
	}


	public void setTotalReview(long totalReview) {
		this.totalReview = totalReview;
	}


	public long getTotalReview5Star() {
		return totalReview5Star;
	}


	public void setTotalReview5Star(long totalReview5Star) {
		this.totalReview5Star = totalReview5Star;
	}
	
}
