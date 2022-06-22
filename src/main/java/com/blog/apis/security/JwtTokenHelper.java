package com.blog.apis.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	@Value("${jwt.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieving any info from token, we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		/*
		 * Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		 * if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		 * claims.put("isAdmin", true); } if (roles.contains(new
		 * SimpleGrantedAuthority("ROLE_USER"))) { claims.put("isUser", true); }
		 */
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// while creating the token
	private String doGenerateToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs * 100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
