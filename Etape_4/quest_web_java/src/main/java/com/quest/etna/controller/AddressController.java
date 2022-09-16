package com.quest.etna.controller;

import java.util.Optional;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
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
import com.quest.etna.repositories.AddressRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable int id,
										   @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (dbAddress.isPresent()) {
			if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
				return ResponseEntity.ok(dbAddress.get());
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.headers(headers)
						.body( "{\"Message\": \"Forbidden to get this address\"}");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Address>> getAll(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		if(hasRole("ROLE_ADMIN")) {
			Iterable<Address> dbAddress = addressRepo.findAll();
			return ResponseEntity.ok(dbAddress);
		} else {
			User userAuthenticated = userRepository.findByUsernameIgnoreCase(authentication.getName());
			Iterable<Address> dbAddress = addressRepo.findAllByUser(userAuthenticated);
			return ResponseEntity.ok(dbAddress);
		}
	}
	
	@PostMapping
	public ResponseEntity<Address> create(@RequestBody Address address,
										  @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		String currentUser = authentication.getName();
		User user = userRepository.findByUsernameIgnoreCase(currentUser);
		address.setUser(user);
		try {
			Address savedAddress = addressRepo.save(address);
			return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			if (address.getCity() == null ||
					address.getCountry() == null ||
					address.getPostalCode() == null ||
					address.getStreet() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address>update(@PathVariable int id, @RequestBody Address formAddress,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
			if (dbAddress.isPresent()) {
				Address updatedAddress = updateAddress(formAddress, dbAddress.get());
				updatedAddress = addressRepo.save(updatedAddress);
				return ResponseEntity.ok(updatedAddress);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.valueOf("application/json"));
		String FalseBody = "{\"success\": \"FALSE\"}";
		if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
			if(dbAddress.isPresent()) {
				try {
					addressRepo.delete(dbAddress.get());
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
	 * Updates and returns an address object.
	 * 
	 * @param updatedAddressInfo new info
	 * @param addressToUpdate address that needs to be updated with the new info
	 * @return the updated address
	 */
	private Address updateAddress(Address updatedAddressInfo, Address addressToUpdate) {
		if (null != updatedAddressInfo.getStreet()) {
			addressToUpdate.setStreet(updatedAddressInfo.getStreet());
		}
		if (null != updatedAddressInfo.getPostalCode()) {
			addressToUpdate.setPostalCode(updatedAddressInfo.getPostalCode());
		}
		if (null != updatedAddressInfo.getCity()) {
			addressToUpdate.setCity(updatedAddressInfo.getCity());
		}
		if (null != updatedAddressInfo.getCountry()) {
			addressToUpdate.setCountry(updatedAddressInfo.getCountry());
		}
		return addressToUpdate;
	}

	public boolean hasRole (String roleName)
	{
	    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}

	public boolean isOwner(Authentication authentication, int id) {
		String currentUser = authentication.getName();
		User userAuthenticated = userRepository.findByUsernameIgnoreCase(currentUser);
		User userOwner = addressRepo.findById(id).get().getUser();
		return userOwner.equals(userAuthenticated);
	}
}
