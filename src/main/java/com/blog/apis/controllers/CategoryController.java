package com.blog.apis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.payloads.ApiResponse;
import com.blog.apis.payloads.CategoryDto;
import com.blog.apis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
		CategoryDto cat = this.categoryService.createCategory(dto);
		return new ResponseEntity<CategoryDto>(cat, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto, @PathVariable Integer catId) {
		CategoryDto cat = this.categoryService.updateCategory(dto, catId);
		return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity(new ApiResponse("Category is Deleted successfully !!", true), HttpStatus.OK);
	}

	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto dto = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
	}

	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(this.categoryService.getCategories());
	}

}
