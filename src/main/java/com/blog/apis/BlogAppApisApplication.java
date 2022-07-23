package com.blog.apis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.apis.configs.AppConstants;
import com.blog.apis.entities.UserRole;
import com.blog.apis.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abc"));

		try {
			// admin role
			UserRole adminRole = new UserRole();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setRole("ROLE_ADMIN");
			// normal role
			UserRole normalRole = new UserRole();
			normalRole.setId(AppConstants.NORMAL_USER);
			normalRole.setRole("ROLE_NORMAL");

			List<UserRole> roles = List.of(adminRole, normalRole);
			List<UserRole> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> System.out.println(r.getRole()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
