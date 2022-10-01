package com.quest.etna.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import com.quest.etna.model.Address;
import com.quest.etna.model.DTO.AddressDTO;


import com.quest.etna.service.IAddressService;
import com.quest.etna.service.JsonService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@Autowired
	private JsonService jsonService;
	
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
