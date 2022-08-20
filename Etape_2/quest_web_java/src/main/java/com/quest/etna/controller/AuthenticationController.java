package com.quest.etna.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public AuthenticationController(UserRepository userRepo) {
		super();
		this.userRepository = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDetails> userRegister(@RequestBody User userRequest) {

		try {
			User user = new User();
			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());
			user.setCreationDate(Instant.now());
			userRepository.save(user);
			UserDetails userDetails = new UserDetails(user.getUsername(), user.getUserRole());
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
}
