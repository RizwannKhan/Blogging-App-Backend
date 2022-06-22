package com.blog.apis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidUserDetailsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private String email;

	public InvalidUserDetailsException(String resourceName, String fieldName, String username) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, username));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.email = username;
	}

	public InvalidUserDetailsException(String resourceName, String fieldName) {
		super(String.format("%s not found with %s", resourceName, fieldName));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
	}

}
