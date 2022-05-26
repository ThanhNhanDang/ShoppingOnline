package com.java.dto;

public class DeliveryAddressDto {
	private long id;
	private long userId;
	private String address;
	private long wardId;
	private long districtId;
	private long provinceId;
	
	public DeliveryAddressDto() {}
	public DeliveryAddressDto(long id, long userId, String address, long wardId, long districtId, long provinceId) {
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.wardId = wardId;
		this.districtId = districtId;
		this.provinceId = provinceId;
	}
	public DeliveryAddressDto(long id, long wardId, long districtId, long provinceId) {
		this.id = id;
		this.wardId = wardId;
		this.districtId = districtId;
		this.provinceId = provinceId;
	}
	public DeliveryAddressDto(long userId, String address, long wardId, long districtId, long provinceId) {
		this.userId = userId;
		this.address = address;
		this.wardId = wardId;
		this.districtId = districtId;
		this.provinceId = provinceId;
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
