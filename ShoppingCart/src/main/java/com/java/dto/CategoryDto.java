package com.java.dto;

public class CategoryDto {
	private long 	id;
	private String	name;
	private String	classFa;
	private long 	quantity;
	
	public CategoryDto() {}

	public CategoryDto(long id, String name, String	classFa) {
		this.id = id;
		this.name = name;
		this.classFa = classFa;
	}
	
	public CategoryDto(long id, String name, String	classFa, long quantity) {
		this.id = id;
		this.name = name;
		this.classFa = classFa;
		this.setQuantity(quantity);
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

	public String getClassFa() {
		return classFa;
	}

	public void setClassFa(String classFa) {
		this.classFa = classFa;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
