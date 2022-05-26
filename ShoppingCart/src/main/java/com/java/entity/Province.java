package com.java.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Province {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;
	
	@OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
	private List<District> districts;
	
	@OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
	private List<DeliveryAddress> addresses;
	
	
	public Province() {}
	
	public Province(long id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
