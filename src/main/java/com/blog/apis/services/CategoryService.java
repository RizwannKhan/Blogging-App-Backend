package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto dto);
	
	//update
	CategoryDto updateCategory(CategoryDto dto, Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get single category
	CategoryDto getCategory(Integer categoryId);
	
	//get all cats
	List<CategoryDto> getCategories();
	
}
