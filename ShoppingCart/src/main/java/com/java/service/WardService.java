package com.java.service;

import java.util.List;

import com.java.entity.Ward;

public interface WardService extends BaseUnitService<Ward, Ward, Long>{
	List<Ward> findWardByDistrictId(long districtId);
}
