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
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;
	private long provinceId;
	
	@ManyToOne
	@JoinColumn(name = "provinceId", insertable = false, updatable = false)
	private Province province;
	
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
	private List<Ward> wards;
	
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
	private List<DeliveryAddress> addresses;
	
	public District() {}
	
	public District(long id, String name, String type, long provinceId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.provinceId = provinceId;
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
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	
}
