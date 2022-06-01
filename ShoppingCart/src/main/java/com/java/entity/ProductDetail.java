package com.java.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long productId;
	private String urlImg;
	private long fileId;
	
	@OneToOne
	@JoinColumn(name = "fileId", insertable=false, updatable=false)
	private FileDB fileDB;
	
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Products products;
	public ProductDetail(long id, long productId, String urlImg, long fileId) {
		this.id = id;
		this.productId = productId;
		this.urlImg = urlImg;
		this.fileId = fileId;
	}
	
	public ProductDetail() {
		// TODO Auto-generated constructor stub
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
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
}
