package com.blog.apis.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty(message = "Username must not be empty or null !!!")
	@Size(min = 4, message = "Username must be min of 4 characters !!!")
	private String name;
	
	@Email(message = "Email address is not valid !!!")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	
	@NotEmpty(message = "Password must not be empty !!!")
	@Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!!")
	private String password;
	
	@NotEmpty(message = "About field must not be empty !!!")
	private String about;

}
