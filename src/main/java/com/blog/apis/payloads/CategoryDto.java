package com.blog.apis.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty(message = "Category title must not be empty or null !!!")
	@Size(min = 4, message = "Title must be of min 4 or greater")
	private String categoryTitle;
	
	@Size(min = 10, message = "Title must be of min 10 or greater")
	@NotEmpty(message = "Category description must not be empty or null !!!")
	private String categoryDescription;
	
}
