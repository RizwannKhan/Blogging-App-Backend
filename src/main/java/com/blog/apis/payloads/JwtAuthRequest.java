package com.blog.apis.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;

	private String password;
}
