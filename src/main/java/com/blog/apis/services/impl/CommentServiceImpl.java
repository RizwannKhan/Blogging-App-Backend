package com.blog.apis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Comment;
import com.blog.apis.entities.Post;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.CommentDto;
import com.blog.apis.repositories.CommentRepo;
import com.blog.apis.repositories.PostRepo;
import com.blog.apis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(CommentDto dto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post id", postId));

		Comment comment = this.mapper.map(dto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.mapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment ", "Comment id", commentId));
		this.commentRepo.delete(comment);
	}

}
