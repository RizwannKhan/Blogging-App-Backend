package com.blog.apis.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenHelper helper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get token from request
		// JWT Token is in the form "Bearer token". Remove Bearer word and
		// get only the Token
		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);

		String username = null;
		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = this.helper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.err.println("Unable to get JWT Token");
			} catch (ExpiredJwtException ex) {
				ex.printStackTrace();
				System.err.println("JWT Token has expired");
			} catch (MalformedJwtException me) {
				me.printStackTrace();
				System.err.println("Invalid JWT Token");
			}
		} else {
			System.err.println("JWT Token does not begin with Bearer");
		}

		// Once we get token, then validate it
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (this.helper.validateToken(token, userDetails)) {
				// all working fine, perform Authentication
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.err.println("Invalid JWT Token");
			}
		} else {
			System.err.println("Username is null or Context is null");
		}

		filterChain.doFilter(request, response);
	}

}
