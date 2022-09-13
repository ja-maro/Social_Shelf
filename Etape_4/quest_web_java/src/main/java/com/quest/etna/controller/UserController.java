package com.quest.etna.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable int id) {	
		Optional<User> dbUser = userRepo.findById(id);
		if (dbUser.isPresent()) {
			User user = dbUser.get();
			user.setPassword("*****");
			return ResponseEntity.ok(user);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<Iterable<User>> getAll() {	
		Iterable<User> users = userRepo.findAll();
		users.forEach(user -> user.setPassword("*****"));
		return ResponseEntity.ok(users);
	}
	
	@PutMapping
	public ResponseEntity<User> update() {
		return null;
	}
	
	@DeleteMapping
	public ResponseEntity<User> delete() {
		return null;
	}
	
	
	
	
	public boolean hasRole (String roleName)
	{
	    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
}
