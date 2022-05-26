package com.java.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.dto.ProductsDto;
import com.java.entity.Products;
import com.java.repository.ProductRepository;
import com.java.service.FileService;
import com.java.service.ProductService;

@Service
@Scope("prototype")
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository repository;
	private FileService fileService;
	private Clock cl = Clock.systemDefaultZone();
	
	public ProductServiceImpl(ProductRepository repository, FileService fileService) {
		this.repository = repository;
		this.fileService = fileService;
	}

	@Override
	public List<ProductsDto> findAll()  {
		List<Products> list = repository.findAllReverse();
		List<ProductsDto> dtos = new ArrayList<ProductsDto>();
		DateTimeFormatter formatter =
				DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
	            .withZone(ZoneId.systemDefault());
		for(Products entity : list) {
		
			ProductsDto dto = new ProductsDto(entity.getId(), entity.getCategory_id(),
					entity.getName(), entity.getPrice(), formatter.format( entity.getExprideDate() ), entity.getInStock(), entity.getUnitSold(), entity.getUrlImg(),entity.getDescription(), false, entity.getTotalReview(), entity.getTotalReview5Star());
			
			dtos.add(dto);
		}
		return dtos;
	}


	@Override
	public Integer save(ProductsDto dto) {
		return 0;
	}
	
	@Modifying
	@Transactional
	@Override
	public Integer delete(Long id) {
		if(!this.isExits(id))
			return -1;
		repository.deleteById(id);
		return 0;
	}
	
	

	@Override
	public ProductsDto findById(Long id) {
		if(!this.isExits(id))
			return null;
		ProductsDto dto = repository.getProductById(id);
		Instant instant = dto.getExprideDate();
		DateTimeFormatter formatter =
				DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
	            .withZone(ZoneId.systemDefault());
			               
		
		String output = formatter.format( instant );
		dto.setExDate(output);	
		return dto;
	}

	@Override
	public Page<ProductsDto> getProductsByCategoryId(long categoryId, Pageable pageable) {
		Page<ProductsDto> dtos = repository.getProductsByCategoryId(categoryId, pageable);
		return dtos;
	}

	@Override
	public Page<ProductsDto> getAllProducts(Pageable pageable) {
		Page<ProductsDto> dtos = repository.getAllProducts(pageable);
		return dtos;
	}
	@Modifying
	@Transactional
	@Override
	public boolean updateInSockAndUnitSold(long productId, int quan) throws Exception{
		if(!this.isExits(productId))
			throw new Exception("Unable to pay. Product not found.");
		Products entity = repository.findById(productId).get();
		long inStock = entity.getInStock();
		//check xem trong kho còn hàng không
		if (inStock == 0)
			throw new Exception("Unable to pay. The product \""+ entity.getName() + "\" is currently out of stock. Please wait for the admin to update.");
		//check xem số lượng đặt hàng có vượi quá số lượng trong kho không
		if((inStock - quan) < 0)
			throw new Exception("Unable to pay. The order quantity of the product \""+ entity.getName() + "\" exceeds the quantity in stock. Please update your cart again.");
		entity.setInStock(entity.getInStock() - quan);
		entity.setUnitSold(entity.getUnitSold() + quan);
		repository.save(entity);
		return true;
	}

	@Modifying
	@Transactional
	@Override
	public int editMyAccout(ProductsDto dto) throws Exception {
		if(dto.getInStock() < 0)
			throw new Exception("Invalid unit in stock.");
		if(dto.getPrice() <= 0)
			throw new Exception("Invalid price.");
		long id = dto.getId();
		if(!this.isExits(id))
			return -1;
		Products entity = repository.findById(id).get();
		entity.setCategory_id(dto.getCategory_id());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setExprideDate(dto.getExprideDate());
		entity.setInStock(dto.getInStock());
		entity.setDescription(dto.getDescription());
		entity.setUrlImg(dto.getUrlImg());
		repository.save(entity);
		return 0;
	}
	@Modifying
	@Transactional
	@Override
	public Products saveReturn(ProductsDto dto) throws Exception {
		if (dto.getInStock() < 0) {
			throw new Exception("Invalid unit in stock.");
		}
		Products entity = new Products(dto.getCategory_id(), dto.getName(), dto.getPrice(), Instant.now(cl), dto.getExprideDate(), dto.getInStock(), dto.getUnitSold(), dto.getUrlImg(), dto.getDescription(), 1, 1);
		return repository.save(entity);
	}

	
	@Override
	public List<ProductsDto> deleteAllBySelect(List<ProductsDto> dtos) throws Exception {
		String urlImg;
		if(dtos.size() == 1) {
			if(this.delete(dtos.get(0).getId())==-1)
				return null;
			urlImg = dtos.get(0).getUrlImg();
			
			if (!urlImg.equals("images/base/upload-directory/upload-directory.png")) {
				this.fileService.delefile(dtos.get(0).getCategory_id(), dtos.get(0).getId());
			}
			return this.findAll();
			
		}
		for(ProductsDto dto : dtos) {
			this.delete(dto.getId());
			urlImg = dto.getUrlImg();
			if (!urlImg.equals("images/base/upload-directory/upload-directory.png"))
				this.fileService.delefile(dto.getCategory_id(), dto.getId());
		}
		
		return this.findAll();
	}

	@Override
	public Page<ProductsDto> getProductsSearch(Pageable pageable, String key, long cateId) {
		Page<ProductsDto> dtos;
		//Nếu người dùng không search và không chọn vào category thì chỉ sắp xếp thôi
		if((key.equals("") || key == null) && (cateId == 0)) {
			dtos = repository.getAllProductsSort(pageable);
			return dtos;
		}
		
		//có category nhưng không search
		if(cateId != 0 && (key == "" || key == null)) {
			dtos = repository.getAllProductsSortWithCateId(pageable, cateId);
			return dtos;
		}
		
		//không có category nhưng có seach
		if((!key.equals("") && key != null) && cateId == 0) {
			dtos = repository.getAllProductsSearch(pageable, key);
			return dtos;
		}
		System.out.println("4");	
		dtos = repository.getAllProductsSearchSortWithCateId(pageable, key, cateId);
		return dtos;
	}

	@Modifying
	@Transactional
	@Override
	public boolean updateReviewOfProduct(long id, long numberStar) throws Exception {
		if(!this.isExits(id))
			throw new Exception("Can't review. Product not found.");
		
		//đã check số sao ở bước thêm review rồi nên không cần check ở đây
		
		Products entity = repository.findById(id).get();
		entity.setTotalReview(entity.getTotalReview()+1);
		if(numberStar == 5)
			entity.setTotalReview5Star(entity.getTotalReview5Star()+1);
		
		repository.save(entity);
		return true;
	}

	@Override
	public List<ProductsDto> searchAdmin(String key) {
		List<ProductsDto> dtos = repository.searchAdmin(key);
		return dtos;
	}

	@Override
	public boolean isExits(long id) {
		
		return repository.existsById(id);
	}

	@Override
	public Products isExits(long id, long cateId) {
		// TODO Auto-generated method stub
		return repository.existsByIdCateId(id, cateId);
	}

}
