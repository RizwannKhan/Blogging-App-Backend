package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.PostDto;
import com.blog.apis.payloads.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto dto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto dto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	// get single post
	PostDto getPostById(Integer postId);

	// get all posts by category
	PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

	// get all posts by user
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

	// search post
	List<PostDto> searchPost(String keyword);

}
