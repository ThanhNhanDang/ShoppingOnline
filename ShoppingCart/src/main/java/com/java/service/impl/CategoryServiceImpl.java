package com.java.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.dto.CategoryDto;
import com.java.entity.Category;
import com.java.repository.CategoryRepositoty;
import com.java.service.CategoryService;


@Service
@Scope("prototype")
public class CategoryServiceImpl implements CategoryService{

	private CategoryRepositoty cateRepo;
	
	public CategoryServiceImpl(CategoryRepositoty cateRepo) {
		this.cateRepo = cateRepo;
	}

	@Override
	public List<CategoryDto> findAll()  {
		List<Category> categories = cateRepo.findAll();
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
		for(Category category : categories) {
			CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(),category.getClassFa(), category.getProducts().size());
			categoryDtos.add(categoryDto);
		}
		return categoryDtos;
	}

	
	@Modifying
	@Transactional
	@Override
	public int updateAllCategory(List<CategoryDto> categoryDtos) {
		List<Category> entities = new ArrayList<Category>();
		for(CategoryDto dto: categoryDtos) {
			Category entity = cateRepo.findById(dto.getId()).get();
			entity.setName(dto.getName());
			entity.setClassFa(dto.getClassFa());
			entities.add(entity);
		}
		cateRepo.saveAll(entities);
		return 0;
	}

	@Override
	public CategoryDto save(CategoryDto dto) {
		Category entity = new Category(dto.getName(), dto.getClassFa());
		cateRepo.save(entity);
		return dto;
	}

	@Modifying
	@Transactional
	@Override
	public List<CategoryDto> delete(long id) throws Exception{
		if(!cateRepo.existsById(id))
			throw new Exception("Can't delete.Category not found!");
		
		cateRepo.deleteById(id);
		List<CategoryDto> dtos = this.findAll(); 
		return dtos;
	}
	
}
