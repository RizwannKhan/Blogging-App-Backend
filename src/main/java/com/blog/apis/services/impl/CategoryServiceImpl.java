package com.blog.apis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Category;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.CategoryDto;
import com.blog.apis.repositories.CategoryRepo;
import com.blog.apis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto dto) {
		Category cat = this.mapper.map(dto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.mapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto dto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		cat.setCategoryTitle(dto.getCategoryTitle());
		cat.setCategoryDescription(dto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(cat);
		return this.mapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));

		return this.mapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> cats = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = cats.stream().map((cat) -> this.mapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return catDtos;
	}

}
