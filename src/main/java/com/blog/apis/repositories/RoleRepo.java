package com.blog.apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.entities.UserRole;

public interface RoleRepo extends JpaRepository<UserRole, Integer>{

}
