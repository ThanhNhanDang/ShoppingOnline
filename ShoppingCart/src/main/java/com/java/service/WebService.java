package com.java.service;

import java.util.List;

import com.java.dto.CheckOutCartDto;

public interface WebService {
	//findVisitorsOrdersForAYear()
	List<CheckOutCartDto> findProvincesWithTheMostOrders();
}
	