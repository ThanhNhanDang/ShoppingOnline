package com.java.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.java.dto.DeliveryAddressDto;

public interface DeliveryAddressService{
	DeliveryAddressDto save(DeliveryAddressDto dto);
	
	boolean checkAddress(DeliveryAddressDto dto);
	boolean checkForExist(long userId, long wardId);
	List<DeliveryAddressDto> findByUser(Long userId);
	ResponseEntity<?> checkOut(HashMap<String, String> checkoutOrderRequest);
	
	int update(DeliveryAddressDto dto) throws Exception;
	void delete(long id, long userId, long deliveryAddressId) throws Exception;
}
