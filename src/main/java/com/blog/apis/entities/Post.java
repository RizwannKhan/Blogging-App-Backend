package com.blog.apis.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_posts")
@NoArgsConstructor
@Getter
@Setter
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "post_title", nullable = false)
	private String title;
	
	@Column(name = "post_content", length = 10000)
	private String content;
	
	@Column(name = "post_image")
	private String imageName;
	
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();

}
