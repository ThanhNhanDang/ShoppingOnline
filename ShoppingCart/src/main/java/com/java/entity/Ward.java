package com.java.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;
	private long districtId;
	
	@ManyToOne
	@JoinColumn(name = "districtId", insertable = false, updatable = false)
	private District district;
	
	@OneToMany(mappedBy = "ward", fetch = FetchType.LAZY)
	private List<DeliveryAddress> addresses;
	
	public Ward() {}
	public Ward(long id, String name, String type, long districtId) {
		
		this.name = name;
		this.type = type;
		this.districtId = districtId;
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
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	
	
}
