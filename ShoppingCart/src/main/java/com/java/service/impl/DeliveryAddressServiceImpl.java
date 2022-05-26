package com.java.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.config.ShoppingConfig;
import com.java.dto.AddToCartDto;
import com.java.dto.CheckOutCartDto;
import com.java.dto.DeliveryAddressDto;
import com.java.entity.CheckOutCart;
import com.java.entity.DeliveryAddress;
import com.java.entity.Ward;
import com.java.model.Message;
import com.java.repository.DeliveryAddressRepository;
import com.java.service.CartService;
import com.java.service.DeliveryAddressService;
import com.java.service.ProductService;

@Service
@Scope("prototype")
public class DeliveryAddressServiceImpl implements DeliveryAddressService{
	private DeliveryAddressRepository repo;
	private CartService cartService;
	private ProductService productService;
	
	public DeliveryAddressServiceImpl(DeliveryAddressRepository repo, CartService cartService, ProductService productService) {
		this.repo = repo;
		this.cartService = cartService;
		this.productService = productService;
	}



	@Override
	public List<DeliveryAddressDto> findByUser(Long userId) {
		
		List<DeliveryAddress> entities = repo.getByUserId(userId);
		List<DeliveryAddressDto> dtos = new ArrayList<DeliveryAddressDto>();
		for(DeliveryAddress entity : entities) {
			DeliveryAddressDto dto = new DeliveryAddressDto(entity.getId(), entity.getUserId(), entity.getAddress(), entity.getWardId(), entity.getDistrictId(), entity.getProvinceId())
;			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public DeliveryAddressDto save(DeliveryAddressDto dto) {
		DeliveryAddress entity =  new DeliveryAddress(dto.getUserId(), dto.getAddress(), dto.getProvinceId(), dto.getDistrictId(), dto.getWardId());
		repo.save(entity);
		return dto;
	}

	@Override
	public boolean checkAddress(DeliveryAddressDto dto) {
		Ward entity = repo.checkAddress(dto.getWardId());
		if(entity == null) // nếu không tìm thấy địa chỉ có wardId => flase
			return false;
		
		// nếu tìm thấy thì so sánh với provinceId và districstId của CSDL với provinceId và districstId của request => true nếu đúng 
		
		if (entity.getDistrictId() != dto.getDistrictId() )
			return false;
		
		return true;
	}

	@Override
	public boolean checkForExist(long userId, long wardId) {
		if (repo.checkForExist(userId, wardId) == null)
			return true;
		return false;
	}

	@Override
	public ResponseEntity<?> checkOut(HashMap<String, String> checkoutOrderRequest) {
		try {
			String keys[] = {"userId", "totalPrice", "paymentType", "deliveryAddress", "provinceId", "districtId", "wardId"};
			if(ShoppingConfig.validationWithHashMap(keys, checkoutOrderRequest)) {}
			long userId = Long.valueOf(checkoutOrderRequest.get("userId"));
			long districtId = Long.valueOf(checkoutOrderRequest.get("districtId"));
			long provinceId = Long.valueOf(checkoutOrderRequest.get("provinceId"));
			long wardId = Long.valueOf(checkoutOrderRequest.get("wardId"));
			String deliveryAddress =  checkoutOrderRequest.get("deliveryAddress");
			boolean checkAddress = false;
			List<AddToCartDto> cartDtos = cartService.getCartByUserId(userId);
			if(cartDtos.isEmpty())
				throw new Exception("No products found in your shopping cart");
			DeliveryAddressDto addressDto = new DeliveryAddressDto();
			addressDto.setProvinceId(provinceId);
			addressDto.setDistrictId(districtId);
			addressDto.setWardId(wardId);
			checkAddress = this.checkAddress(addressDto);
			if(!checkAddress) {
				throw new Exception("Address not found");
			}
		
			
			if(this.checkForExist(userId, wardId)) {
				addressDto.setAddress(deliveryAddress);
				
				addressDto.setUserId(userId);
				this.save(addressDto);
			}
			
			double totalPrice = Double.valueOf(checkoutOrderRequest.get("totalPrice"));
			
			if(cartService.checkTotalPriceAgainstCart(totalPrice, userId)) {
				List<CheckOutCart> entities = new ArrayList<CheckOutCart>();
				for (AddToCartDto cartDto : cartDtos) {
					
					long productId = cartDto.getProduct_id();
					int quantity = cartDto.getQuantity();
					if (!productService.updateInSockAndUnitSold(productId, quantity)){}
					String orderId = "" + cartService.getOrderId();
					CheckOutCart entity = new CheckOutCart();
					entity.setProductId(productId);
					entity.setQuantity(quantity);
					entity.setPaymentType(checkoutOrderRequest.get("paymentType"));
					entity.setDeliveryAddress(deliveryAddress);
					entity.setOrderDate(Instant.now());
					System.out.println(entity.getOrderDate());
					entity.setUserId(userId);
					entity.setPrice(totalPrice);
					entity.setOrderId(orderId);
					entities.add(entity);
				}
				cartService.addProductsForCheckOutCart(entities);
				List<CheckOutCartDto> checkOutDtos = cartService.getCheckOutCartByUserId(userId);
				return new ResponseEntity<>(checkOutDtos, HttpStatus.CREATED);
			}
			throw new Exception("Total amount is mismatch");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/order/checkout-order"),HttpStatus.BAD_REQUEST);
		}
	}



	@Override
	public int update(DeliveryAddressDto dto) throws Exception{
		DeliveryAddress entity = repo.checkExist(dto.getId(), dto.getUserId());
		if (entity == null)
			throw new Exception("Not found");
		DeliveryAddress entity2 = repo.checkForExist(dto.getUserId(), dto.getWardId());
		if(entity2 != null && entity2.getId() != dto.getId())
			throw new Exception("ward already exists.");
		
		entity.setAddress(dto.getAddress());
		entity.setDistrictId(dto.getDistrictId());
		entity.setProvinceId(dto.getProvinceId());
		entity.setWardId(dto.getWardId());
		repo.save(entity);
		return 0;
	}



	@Override
	public void delete(long id, long userId, long deliveryAddressId) throws Exception {
		if(deliveryAddressId == id)
			throw new Exception("You have selected this address as your current shipping address, if you wish to remove this address from the list, please update your current shipping address.");
		repo.deleteByUser(id, userId);
		
	}
	
	

}
