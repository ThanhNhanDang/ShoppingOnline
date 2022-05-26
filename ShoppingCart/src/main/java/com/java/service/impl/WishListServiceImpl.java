package com.java.service.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.dto.WishListDto;
import com.java.entity.WishList;
import com.java.repository.ProductRepository;
import com.java.repository.WishListRepository;
import com.java.service.WishListService;

@Service
@Scope("prototype")
public class WishListServiceImpl implements WishListService{
	private WishListRepository repo;
	
	private ProductRepository productRepository;
	public WishListServiceImpl(WishListRepository repo, ProductRepository productRepository) {
		this.repo = repo;
		this.productRepository = productRepository;
	}


	@Override
	public List<WishListDto> delete(long id, long userId) {
		repo.deleteByUserId(id, userId);
		return this.getAllByUser(userId);
	}

	@Override
	public List<WishListDto> getAllByUser(long userId) {
		return repo.getAllByUser(userId);
	}


	@Override
	public int addByUserAndProduct(long userId, long productId, int quantity) throws Exception {
		if(repo.getWishListByProductIdAndUserId(userId,productId).isPresent()) {
			throw new Exception("Product is already exist.");
		}
		
		WishList entity = new WishList(productId, productRepository.findById(productId).get().getPrice(), quantity, userId);
		if(repo.save(entity) == null)
			return -1;
		return 0;
		
	}


	@Override
	public WishListDto getItem(long userId, long wishlistId) throws Exception{
		WishListDto dto = repo.getItem(userId, wishlistId);
		if(dto == null)
			throw new Exception("This product was not found.");
		return dto;
	}

}
