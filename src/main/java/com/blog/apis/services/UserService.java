package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto dto);

	//create user...
	UserDto createUser(UserDto userDto);
	
	//update user..
	UserDto updateUser(UserDto userDto, Integer userId);
	
	//get user by id...
	UserDto getUserById(Integer userId);
	
	//get all users...
	List<UserDto> getAllUsers();
	
	//delete user...
	void deleteUser(Integer userId);
	
}
