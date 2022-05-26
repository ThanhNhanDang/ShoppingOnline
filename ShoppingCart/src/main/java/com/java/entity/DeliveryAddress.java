package com.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DeliveryAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private long 	userId;
	private String 	address;
	private long wardId;
	private long districtId;
	private long provinceId;
	
	
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private User	user;
	
	@ManyToOne
	@JoinColumn(name = "wardId", insertable = false, updatable = false)
	private Ward	ward;
	
	@ManyToOne
	@JoinColumn(name = "districtId", insertable = false, updatable = false)
	private District	district;
	
	@ManyToOne
	@JoinColumn(name = "provinceId", insertable = false, updatable = false)
	private Province	province;
	
	public DeliveryAddress() {}
	
	public DeliveryAddress(long id, long userId, String address, long provinceId, long districtId,
			long wardId) {
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.provinceId = provinceId;
		this.districtId = districtId;
		this.wardId = wardId;
	}

	public DeliveryAddress( long userId, String address, long provinceId, long districtId,
			long wardId) {
		
		this.userId = userId;
		this.address = address;
		this.provinceId = provinceId;
		this.districtId = districtId;
		this.wardId = wardId;
	}
	
	public DeliveryAddress(String address, long provinceId, long districtId,
			long wardId) {
		this.address = address;
		this.provinceId = provinceId;
		this.districtId = districtId;
		this.wardId = wardId;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getWardId() {
		return wardId;
	}

	public void setWardId(long wardId) {
		this.wardId = wardId;
	}

	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}	
	
}
