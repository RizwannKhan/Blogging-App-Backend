package com.blog.apis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Category;
import com.blog.apis.entities.Post;
import com.blog.apis.entities.User;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.PostDto;
import com.blog.apis.payloads.PostResponse;
import com.blog.apis.repositories.CategoryRepo;
import com.blog.apis.repositories.PostRepo;
import com.blog.apis.repositories.UserRepo;
import com.blog.apis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto dto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));

		Post post = this.mapper.map(dto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto dto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
		post.setTitle(dto.getTitle());
		post.setContent(dto.getContent());
		post.setImageName(dto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

		Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse response = new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(pagePosts.getNumber());
		response.setPageSize(pagePosts.getSize());
		response.setTotalElements(pagePosts.getTotalElements());
		response.setTotalPages(pagePosts.getTotalPages());
		response.setLastPage(pagePosts.isLast());
		return response;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
		PostDto dto = this.mapper.map(post, PostDto.class);
		return dto;
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		Page<Post> pagePosts = this.postRepo.findByCategory(category, pageable);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse response = new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(pagePosts.getNumber());
		response.setPageSize(pagePosts.getSize());
		response.setTotalElements(pagePosts.getTotalElements());
		response.setTotalPages(pagePosts.getTotalPages());
		response.setLastPage(pagePosts.isLast());
		return response;
	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", userId));
		Page<Post> pagePosts = this.postRepo.findByUser(user, pageable);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse response = new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(pagePosts.getNumber());
		response.setPageSize(pagePosts.getSize());
		response.setTotalElements(pagePosts.getTotalElements());
		response.setTotalPages(pagePosts.getTotalPages());
		response.setLastPage(pagePosts.isLast());
		return response;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
		List<PostDto> postDtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
