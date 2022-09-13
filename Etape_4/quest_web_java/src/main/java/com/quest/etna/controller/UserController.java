package com.quest.etna.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable int id, @RequestBody User formUser, @CurrentSecurityContext(expression = "authentication") Authentication auth) {	
		Optional<User> dbUser = userRepo.findById(id);
		if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
			if (dbUser.isPresent()) {		
				User updatedUser = updateUsername(formUser, dbUser.get());
				if (hasRole("ROLE_ADMIN")) {
					updatedUser = updateRole(formUser, updatedUser);
				}
				updatedUser = userRepo.save(updatedUser);
				return ResponseEntity.ok(updatedUser);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * Updates username of user if a new one was given.
	 * 
	 * @param formUser contains new username
	 * @param user
	 * @return
	 */
	private User updateRole(User formUser, User user) {
		if (null != formUser.getUserRole()) {
			user.setUserRole(formUser.getUserRole());
		}
		return user;
	}

	/**
	 * Updates role of user if a new one was given.
	 * 
	 * @param formUser contains new role
	 * @param user
	 * @return
	 */
	private User updateUsername(User formUser, User user) {
		if (null != formUser.getUsername()) {
			user.setUsername(formUser.getUsername());
		}
		return user;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
		Optional<User> dbUser = userRepo.findById(id);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.valueOf("application/json"));
		String FalseBody = "{\"success\": \"FALSE\"}";
		if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
			if(dbUser.isPresent()) {
				try {
					userRepo.delete(dbUser.get());
					return new ResponseEntity<>("{\"success\": \"TRUE\"}", responseHeader, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(FalseBody, responseHeader, HttpStatus.FORBIDDEN);
				}
			} else {
				return new ResponseEntity<>(FalseBody, responseHeader, HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(FalseBody, responseHeader, HttpStatus.FORBIDDEN);
	}

	/**
	 * Checks whether current authenticated user has given role
	 * 
	 * @param roleName
	 * @return
	 */
	public boolean hasRole (String roleName)
	{
	    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
	
	/**
	 * Checks whether current authenticated user is given user
	 * 
	 * @param authentication
	 * @param id
	 * @return
	 */
	public boolean isUser(Authentication authentication, int id) {
		String currentUser = authentication.getName();
		User userAuthenticated = userRepo.findByUsernameIgnoreCase(currentUser);
		User userToModify = userRepo.findById(id).get();
		return userToModify.equals(userAuthenticated);
	}
}
