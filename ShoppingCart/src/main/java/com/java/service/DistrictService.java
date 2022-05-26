package com.java.service;

import java.util.List;

import com.java.entity.District;

public interface DistrictService extends BaseUnitService<District, District, Long>{
	List<District>  findDistrictByProvinceId(long provinceId);
}
