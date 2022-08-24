package com.quest.etna.controller;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.model.JwtUserDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;

import java.time.Instant;

@RestController
public class AuthenticationController {

	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;


	public AuthenticationController(UserRepository userRepo,
									UserDetailsService userDetailsService,
									JwtTokenUtil jwtTokenUtil) {
		super();
		this.userRepository = userRepo;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDetails> userRegister(@RequestBody User userRequest) {

		try {
			User user = new User();
			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());
			user.setCreationDate(Instant.now());
			userRepository.save(user);
			UserDetails userDetails = new UserDetails(user);
			return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException error) {
			if (userRequest.getPassword() == null || userRequest.getUsername() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
			}
			throw new ResponseStatusException(HttpStatus.CONFLICT, error.getMessage());
		} catch (Exception error) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticateUser(@RequestBody User userRequest) {
		try {
			User user = new User();
			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());

			Authentication token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			SecurityContextHolder.getContext().setAuthentication(token);

			JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(user.getUsername());
			String tokenFinal = jwtTokenUtil.generateToken(userDetails);
			return new ResponseEntity<>(tokenFinal, HttpStatus.OK);
		} catch (Exception error) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
		}
	}
}
