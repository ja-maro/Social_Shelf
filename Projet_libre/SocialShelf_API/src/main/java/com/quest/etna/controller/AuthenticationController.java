package com.quest.etna.controller;

import com.quest.etna.config.JwtTokenUtil;

import com.quest.etna.model.jwt.JwtRequest;
import com.quest.etna.model.jwt.JwtResponse;
import com.quest.etna.model.jwt.JwtUserDetails;
import com.quest.etna.model.player.Player;
import com.quest.etna.model.player.PlayerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.repositories.PlayerRepository;

import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<PlayerDetails> userRegister(@RequestBody Player userRequest) {

		try {
			Player user = new Player();
			user.setUsername(userRequest.getUsername());
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setEmail(userRequest.getEmail());
			playerRepository.save(user);

			PlayerDetails playerDetails = new PlayerDetails(user);
			return new ResponseEntity<>(playerDetails, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException error) {
			if (userRequest.getPassword() == null || userRequest.getUsername() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
			}
			throw new ResponseStatusException(HttpStatus.CONFLICT, error.getMessage());
		} catch (Exception error) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
		}
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authRequest) throws Exception {
		authenticate(authRequest.getUsername(), authRequest.getPassword());
		final JwtUserDetails userDetails = (JwtUserDetails) userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (AuthenticationException authExc){
			throw new RuntimeException("Invalid Login Credentials");
		}
	}

	@GetMapping(value = "/me")
	public ResponseEntity<PlayerDetails> me(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		String username = authentication.getName();
		PlayerDetails playerDetails = new PlayerDetails(playerRepository.findByUsernameIgnoreCase(username));
		return new ResponseEntity<>(playerDetails, HttpStatus.OK);
	}
}
