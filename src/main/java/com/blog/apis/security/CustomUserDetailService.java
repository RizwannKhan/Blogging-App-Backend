package com.blog.apis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.User;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from DB by username
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Email ", username));
		return user;
	}

}
