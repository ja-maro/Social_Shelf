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
import com.quest.etna.model.UserRole;
import com.quest.etna.repositories.UserRepository;

@RestController
public class AuthenticationController {

	private final UserRepository userRepository;

	public AuthenticationController(UserRepository userRepo) {
		super();
		this.userRepository = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<UserDetails> register(@RequestBody User requestUser) {

		try {
			UserDetails userDetails = new UserDetails(requestUser.getUsername(), UserRole.ROLE_USER);
			if (!userRepository.existsByUsernameIgnoreCase(requestUser.getUsername())) {
				User user = new User(requestUser);
				userRepository.save(user);

				return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(userDetails, HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException error) {
			if (requestUser.getPassword() == null || requestUser.getUsername() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
			}
		    throw new ResponseStatusException(HttpStatus.CONFLICT, error.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
