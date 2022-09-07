package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Address> getById(@PathVariable int id) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (dbAddress.isPresent()) {
			return ResponseEntity.ok(dbAddress.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
		}
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Address>> getAll() {
		Iterable<Address> dbAddress = addressRepo.findAll();
		return ResponseEntity.ok(dbAddress);
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
	public ResponseEntity<Address>update(@RequestParam int id, @RequestBody Address formAddress) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (dbAddress.isPresent()) {
			Address updatedAddress = updateAddress(formAddress, dbAddress.get());		
			updatedAddress = addressRepo.save(updatedAddress);
			return ResponseEntity.ok(updatedAddress);		
		} else {
			return new ResponseEntity<>(formAddress, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@RequestParam int id) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		try {
			addressRepo.delete(dbAddress.get());
			return new ResponseEntity<String>("{\"success\": \"TRUE\"}", HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<String>("{\"success\": \"FALSE\"}", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("{\"success\": \"FALSE\"}", HttpStatus.OK);
		}
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
	
	/**
	 * Checks if current user has given role
	 * 
	 * @param roleName
	 * @return
	 */
	public static boolean hasRole (String roleName)
	{
	    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
}
