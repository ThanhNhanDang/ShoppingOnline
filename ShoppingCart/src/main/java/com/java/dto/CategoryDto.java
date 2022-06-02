package com.java.dto;

public class CategoryDto {
	private long 	id;
	private String	name;
	private long 	quantity;
	
	public CategoryDto() {}

	public CategoryDto(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDto(long id, String name, long quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
