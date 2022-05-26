package com.java.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private String	name;
	private String	classFa;
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Products> products;
	
	public Category() {}

	public Category(long id, String name, String classFa) {
		this.id = id;
		this.name = name;
		this.classFa = classFa ;
	}
	
	public Category(String name, String classFa) {
		this.name = name;
		this.classFa = classFa ;
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
	public List<Products> getProducts() {
		return products;
	}
}
