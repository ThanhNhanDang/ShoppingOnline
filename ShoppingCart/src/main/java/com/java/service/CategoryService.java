package com.java.service;

import java.util.List;

import com.java.dto.CategoryDto;

public interface CategoryService {
	List<CategoryDto> findAll();
	int updateAllCategory(List<CategoryDto> categoryDtos);
	CategoryDto save(CategoryDto dto);
	List<CategoryDto> delete(long id) throws Exception;
}
