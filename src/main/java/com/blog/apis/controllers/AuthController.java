package com.blog.apis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.exceptions.InvalidUserDetailsException;
import com.blog.apis.payloads.JwtAuthRequest;
import com.blog.apis.payloads.JwtAuthResponse;
import com.blog.apis.payloads.UserDto;
import com.blog.apis.security.JwtTokenHelper;
import com.blog.apis.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper helper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest)
			throws InvalidUserDetailsException {
		this.authenticate(authRequest.getUsername(), authRequest.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
		String token = this.helper.generateToken(userDetails);

		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws InvalidUserDetailsException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
//			e.printStackTrace();
			System.err.println("Invalid Details !!!");
			throw new InvalidUserDetailsException("User", "Username or Password");
		}
	}

	// Register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto dto) {
		UserDto registeredUser = this.userService.registerUser(dto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

}
