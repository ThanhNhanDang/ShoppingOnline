package com.java.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.dto.AddToCartDto;
import com.java.dto.CheckOutCartDto;
import com.java.entity.AddToCart;
import com.java.entity.CheckOutCart;
import com.java.repository.AddToCartRepository;
import com.java.repository.CheckOutCartRepository;
import com.java.repository.ProductRepository;
import com.java.service.CartService;


@Service
@Scope("prototype")
public class CartServiceImpl implements CartService{
	private AddToCartRepository cartRepo;
	private ProductRepository productRepository;
	private CheckOutCartRepository checkOutCartRepository;
	private Clock cl = Clock.systemDefaultZone();
	private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
	public CartServiceImpl(AddToCartRepository cartRepo, ProductRepository productRepository, CheckOutCartRepository checkOutCartRepository) {
		this.cartRepo = cartRepo;
		this.productRepository = productRepository;
		this.checkOutCartRepository = checkOutCartRepository;
	}

	@Override
	public List<AddToCartDto> addCartByUserIdAndProductId(long userId, long productId, int quantity) throws Exception{
		try {
			if(cartRepo.getCartByProductIdAndUserId(userId, productId).isPresent()) {
				throw new Exception("Product is already exist.");
			}
				
			AddToCart addToCart = new AddToCart();
			addToCart.setUser_id(userId);
			addToCart.setQuantity(quantity);
			
			addToCart.setAdded_date(Instant.now(cl));
			addToCart.setProduct_id(productId);
			//Kiểm tra price có đúng với quantity 
			double price = productRepository.findById(productId).get().getPrice();
			addToCart.setPrice(price*quantity);
			
			cartRepo.save(addToCart);
			return this.getCartByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		
	}

	@Override
	public List<AddToCartDto> getCartByUserId(long userId) {
		List<AddToCartDto> addToCartDtos = cartRepo.getCartByUserId(userId);
		return addToCartDtos;
	}

	@Override
	public List<AddToCartDto> deleteCartByUserId(long id, long userId) {
		cartRepo.deleteCartByIdAndUserId(id, userId);
		return this.getCartByUserId(userId);
		
	}

	@Modifying
	@Transactional
	@Override
	public void updateQtyByCartId(List<AddToCartDto> dtos) throws Exception {
		List<AddToCart> entities = new ArrayList<AddToCart>();
		for(AddToCartDto dto: dtos) {
			AddToCart entity = cartRepo.findById(dto.getId()).get();
			entity.setQuantity(dto.getQuantity());
			entity.setPrice(dto.getQuantity()*dto.getPricePerItem());
			entity.setAdded_date(Instant.now(cl));
			entities.add(entity);
		}
		cartRepo.saveAll(entities);

	}

	@Override
	public boolean checkTotalPriceAgainstCart(double totalPrice, long userId) {
		double totalPriceDB = cartRepo.getTotalPriceByUserId(userId);
		if ((totalPriceDB+3) == totalPrice)
			return true;
		return false;
	}

	@Override
	public List<CheckOutCartDto> getCheckOutCartByUserId(long userId) {
		List<CheckOutCartDto> dtos = checkOutCartRepository.getCheckOutCartByUserId(userId);
		return dtos;
	}

	@Override
	public void addProductsForCheckOutCart(List<CheckOutCart> entities) throws Exception{
		
		try {
			if(entities.size() > 0) {
				checkOutCartRepository.saveAll(entities);
				this.deleteAllCartByUserId(entities.get(0).getUserId());
			}
			else {
				throw new Exception("Shound not be empty");
			}
			
			
		} catch (Exception e) {
			throw new Exception("Error while check out " + e.getMessage());
		}
	}

	@Override
	public int getOrderId() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}

	@Override
	public List<AddToCartDto> deleteAllCartByUserId(long userId) {
		cartRepo.deleteAllCartByUserId(userId);
		return null;
	}

	@Override
	public void updateItem(double price, long id, int quantity) throws Exception {
		if (cartRepo.existsById(id)) {
			AddToCart entity = cartRepo.findById(id).get();
			entity.setQuantity(quantity);
			entity.setPrice(quantity*price);
			entity.setAdded_date(Instant.now(cl));
			cartRepo.save(entity);
		}
	}

	@Override
	public AddToCartDto getCartById(long userId, long cartId) {
		AddToCartDto dto = cartRepo.getCartByCartId(userId, cartId);
		if(dto == null)
			return null;
		return dto;
	}

	@Override
	public List<CheckOutCartDto> getAll() {
		return this.checkOutCartRepository.getAll();
	}

}
