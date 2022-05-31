package com.java.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private long 	category_id;
	private String	name;
	private double	price;
	private Instant added_on;
	private Instant exprideDate;
	@Lob
	private String	description;
	private long 	inStock;
	private long 	unitSold;
	private String	urlImg;
	private long 	totalReview;
	private long 	totalReview5Star;
	private long 	fileId;
	@OneToOne
	@JoinColumn(name = "fileId", insertable=false, updatable=false)
	private FileDB fileDB;
	
	@ManyToOne
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private Category category;
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<AddToCart> addToCarts;
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<CheckOutCart> checkOutCarts;
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<ProductDetail> productDetail;
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<WishList> wishList;
	
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	public Products() {}
	
	
	public Products(long id, long category_id, String name, double price, Instant added_on, Instant exprideDate, long inStock, long unitSold, String urlImg, long totalReview, long totalReview5Star, long fileId) {
		this.id = id;
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
		this.fileId = fileId;
	}
	
	public Products(long id, long category_id, String name, double price, Instant added_on, Instant exprideDate, String description, long inStock, long unitSold, String urlImg, long totalReview, long totalReview5Star, long fileId) {
		this.id = id;
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.added_on = added_on;
		this.exprideDate = exprideDate;
		this.description = description;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.totalReview = totalReview;
		this.totalReview5Star = totalReview5Star;
		this.fileId = fileId;
	}



	public Products(long category_id, String name, double price, Instant added_on, Instant exprideDate, long inStock, long unitSold, String	urlImg, String description, long totalReview, long totalReview5Star, long fileId) {
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.added_on = added_on;
		this.exprideDate = exprideDate;
		this.inStock = inStock;
		this.unitSold = unitSold;
		this.urlImg = urlImg;
		this.description = description;
		this.totalReview = totalReview;
		this.totalReview5Star = totalReview5Star;
		this.fileId = fileId;
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

	public Instant getAdded_on() {
		return added_on;
	}

	public void setAdded_on(Instant added_on) {
		this.added_on = added_on;
	}
	
	public Category getCategory() {
		return category;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	
}
