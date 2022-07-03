package com.java.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	private final DateTimeFormatter formatter =
			DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());
	
	public ProductServiceImpl(ProductRepository repository, FileService fileService) {
		this.repository = repository;
		this.fileService = fileService;
	}

	@Override
	public List<ProductsDto> findAll()  {
		List<Products> list = repository.findAllReverse();
		List<ProductsDto> dtos = new ArrayList<ProductsDto>();
		
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
		if(!this.isExits(id)) {
			return null;
		}
		ProductsDto dto = repository.getProductById(id);
		
		String output = formatter.format( dto.getExprideDate2() );
		dto.setExprideDate2(null);
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
	public int edit(ProductsDto dto) throws Exception {
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
		entity.setExprideDate(Instant.parse(dto.getExprideDate()));
		entity.setInStock(dto.getInStock());
		entity.setDescription(dto.getDescription());
		entity.setUrlImg(dto.getUrlImg());
		repository.save(entity);
		return 0;
	}
	@Modifying
	@Transactional
	@Override
	public Products saveReturn(String dto, MultipartFile file) throws Exception {
		ProductsDto dtoJson = new ProductsDto();
		ObjectMapper objectMapper = new ObjectMapper();
		dtoJson = objectMapper.readValue(dto, ProductsDto.class);
		
		if (dtoJson.getInStock() < 0) {
			throw new Exception("Invalid unit in stock.");
		}
	
		UUID uuid = UUID.randomUUID();
		dtoJson.setUrlImg(uuid + ".png");
		Products entity = new Products(dtoJson.getCategory_id(), dtoJson.getName(), dtoJson.getPrice(), Instant.now(cl), Instant.parse(dtoJson.getExprideDate()), dtoJson.getInStock(), dtoJson.getUnitSold(), dtoJson.getUrlImg(), dtoJson.getDescription(), 0, 5);
		entity = repository.save(entity);
		this.fileService.store(file, dtoJson.getUrlImg(), 4, entity.getId());
		return entity;
	}

	
	@Override
	public List<ProductsDto> deleteAllBySelect(List<ProductsDto> dtos) throws Exception {
		if(dtos.size() == 1) {
			if(this.delete(dtos.get(0).getId())==-1)
				return null;
//			this.fileService.deleteFileDB(dtos.get(0).getFileId());
			return this.findAll();
			
		}
		for(ProductsDto dto : dtos) {
			this.delete(dto.getId());
//			this.fileService.deleteFileDB(dto.getFileId());
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
	public boolean updateReviewOfProduct(long id, long numberStar, int average) throws Exception {
		if(!this.isExits(id))
			throw new Exception("Can't review. Product not found.");
		
		//đã check số sao ở bước thêm review rồi nên không cần check ở đây
		
		Products entity = repository.findById(id).get();
		entity.setTotalReview(entity.getTotalReview()+1);
		entity.setTotalReview5Star(average);
		if(numberStar == 5)
			entity.setTotalReview5Star(entity.getTotalReview5Star()+1);
		
		repository.save(entity);
		return true;
	}

	@Override
	public List<ProductsDto> searchAdmin(String key) {
		List<ProductsDto> dtos = new ArrayList<>();
		repository.searchAdmin(key).forEach(item->{
			item.setExDate(formatter.format(item.getExprideDate2()));
			dtos.add(item);
		});;
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

	@Override
	public int edit(String dto, MultipartFile file) throws Exception {
		ProductsDto dtoJson = new ProductsDto();
		ObjectMapper objectMapper = new ObjectMapper();
		dtoJson = objectMapper.readValue(dto, ProductsDto.class);
		
		if(dtoJson.getInStock() < 0)
			throw new Exception("Invalid unit in stock.");
		if(dtoJson.getPrice() <= 0)
			throw new Exception("Invalid price.");
		long id = dtoJson.getId();
		if(!this.isExits(id))
			return -1;
		Products entity = repository.findById(id).get();
		this.fileService.delefile(id, entity.getUrlImg());
		entity.setCategory_id(dtoJson.getCategory_id());
		entity.setName(dtoJson.getName());
		entity.setPrice(dtoJson.getPrice());
		entity.setExprideDate(Instant.parse(dtoJson.getExprideDate()));
		entity.setInStock(dtoJson.getInStock());
		entity.setDescription(dtoJson.getDescription());
		UUID uuid = UUID.randomUUID();
		entity.setUrlImg(uuid + ".png");
		this.fileService.store(file, entity.getUrlImg(), 4, entity.getId());
		repository.save(entity);
		
		return 0;
	}

}
