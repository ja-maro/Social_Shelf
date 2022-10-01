package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.quest.etna.model.player.Player;
import com.quest.etna.model.player.PlayerDTO;
import com.quest.etna.repositories.PlayerRepository;
import com.quest.etna.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.quest.etna.model.Address;
import com.quest.etna.model.AddressDTO;
import com.quest.etna.repositories.AddressRepository;
import org.springframework.web.server.ResponseStatusException;
import com.quest.etna.service.JsonService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private JsonService jsonService;

	@Autowired
	private AddressService addressService;
	
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable int id,
										   @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		return addressService.getById(id, authentication);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<AddressDTO>> getAll(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		return addressService.getAll(authentication);
	}
	
	@PostMapping
	public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO,
										  @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		return addressService.create(addressDTO, authentication);

	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable int id, @RequestBody Address formAddress,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		return addressService.update(id, formAddress, authentication);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		return addressService.delete(id, authentication);
	}
}
