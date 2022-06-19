package com.blog.apis.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.apis.entities.Category;
import com.blog.apis.entities.Post;
import com.blog.apis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

//	List<Post> findByUser(User user);

	Page<Post> findByUser(User user, Pageable pageable);

//	List<Post> findByCategory(Category category);

	Page<Post> findByCategory(Category category, Pageable pageable);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);

}
