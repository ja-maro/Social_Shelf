package com.quest.etna.config;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	private final UserRepository userRepository;

	public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		// Get jwt token
		final String token = header.split(" ")[1].trim();

		// Get user identity and set it on the spring security context
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUserDetails userDetails = new JwtUserDetails(userRepository.findByUsername(username));

		// validate token
		if (jwtTokenUtil.validateToken(token, userDetails)) {

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		chain.doFilter(request, response);
	}
}
