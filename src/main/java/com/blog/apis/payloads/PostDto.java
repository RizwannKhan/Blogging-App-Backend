package com.blog.apis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.apis.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer postId;

	@NotEmpty(message = "Post title must not be empty or null !!!")
	@Size(min = 4, message = "Post title must be of min 4 chars !!!")
	private String title;

	@NotEmpty(message = "Post content must not be empty or null !!!")
	@Size(min = 10, message = "Post content must be of min 10 chars !!!")
	private String content;

	private String imageName;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();

}
