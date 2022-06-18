package com.blog.apis.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.payloads.ApiResponse;
import com.blog.apis.payloads.PostDto;
import com.blog.apis.payloads.PostResponse;
import com.blog.apis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	// create a post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto post = this.postService.createPost(dto, userId, categoryId);

		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
	}

	// get all posts by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		PostResponse posts = this.postService.getPostByUser(userId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get all posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		PostResponse posts = this.postService.getPostByCategory(categoryId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// get single post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto dto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(dto, HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<PostDto> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity(new ApiResponse("Post is Deleted successfully !!", true), HttpStatus.OK);
	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto dto, @PathVariable Integer postId) {
		PostDto postDto = this.postService.updatePost(dto, postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

}
