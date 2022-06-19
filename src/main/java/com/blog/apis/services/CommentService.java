package com.blog.apis.services;

import com.blog.apis.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto dto, Integer postId);

	void deleteComment(Integer commentId);

}
