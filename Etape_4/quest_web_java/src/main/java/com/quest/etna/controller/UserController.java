package com.quest.etna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public ResponseEntity<User> get() {
		return null;
	}

	@GetMapping
	public ResponseEntity<Iterable<User>> getAll() {
		return null;
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
