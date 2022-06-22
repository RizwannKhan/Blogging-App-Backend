package com.blog.apis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private String email;

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName2, String fieldName2, String username) {
		super(String.format("%s not found with %s : %s", resourceName2, fieldName2, username));
		this.resourceName = resourceName2;
		this.fieldName = fieldName2;
		this.email = username;
	}

}
