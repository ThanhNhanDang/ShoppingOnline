package com.java.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.dto.CheckOutCartDto;
import com.java.repository.CheckOutCartRepository;
import com.java.service.WebService;

@Service
@Scope("prototype")
public class WebServiceImpl implements WebService {
	private CheckOutCartRepository checkOutCartRepository;

	public WebServiceImpl(CheckOutCartRepository checkOutCartRepository) {
		super();
		this.checkOutCartRepository = checkOutCartRepository;
	}

	/// findProvincesWithTheMostOrders ///
	@Override
	public List<CheckOutCartDto> findProvincesWithTheMostOrders() {

		List<CheckOutCartDto> dtos = checkOutCartRepository.getAll();
		List<CheckOutCartDto> dtosProvincesWithTheMostOrders = new ArrayList<CheckOutCartDto>();
		List<Long> productIds = new ArrayList<Long>();

		int i = 0;

		productIds.add(dtos.get(i).getProductId());
		dtosProvincesWithTheMostOrders.add(dtos.get(i));
		for (CheckOutCartDto dto : dtos) {
			if (this.checkDuplicate(productIds, dto.getProductId())) {
				productIds.add(dto.getProductId());
				dtosProvincesWithTheMostOrders.add(dto);
				i++;
			}

		}
		i = 0;
		for (CheckOutCartDto dto : dtosProvincesWithTheMostOrders) {
			dto.setCountTheMostOrders(this.countProductOrder(dtos, dto.getProductId()));
		}

		if (dtosProvincesWithTheMostOrders.size() < 10) 
			return dtosProvincesWithTheMostOrders;
		else
			return  this.productMostOrders(dtosProvincesWithTheMostOrders);

	}

	private boolean checkDuplicate(List<Long> productIds, Long productId) {
		for (Long product : productIds) {
			if (product == productId) {
				return false;
			}
		}
		return true;
	}

	private long countProductOrder(List<CheckOutCartDto> dtos, Long productId) {
		long count = 0;
		for (CheckOutCartDto dto : dtos) {
			if (productId == dto.getProductId()) {
				count++;
			}
		}
		return count;

	}
	
	private List<CheckOutCartDto> productMostOrders(List<CheckOutCartDto> dtos){
		Collections.sort(dtos);
		
		List<CheckOutCartDto> dtosProvincesWithTheMostOrders = new ArrayList<CheckOutCartDto>();
		int  j = 0;
		for (int  i = dtos.size()- 1; i >=0; i--) {
			
			if(j >= 9)
				break;
			dtosProvincesWithTheMostOrders.add(dtos.get(i));
			j++;
			
		}
		return dtosProvincesWithTheMostOrders;
	}

	/// findProvincesWithTheMostOrders ///
}
