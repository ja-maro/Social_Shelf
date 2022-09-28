package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.quest.etna.model.Player;
import com.quest.etna.model.PlayerDTO;
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
	private UserRepository userRepository;

	@Autowired
	private JsonService jsonService;
	
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable int id,
										   @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (dbAddress.isPresent()) {
			if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
				AddressDTO addressDTO = new AddressDTO(dbAddress.get());
				return ResponseEntity.ok(addressDTO);
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.headers(headers)
						.body( "{\"message\": \"Forbidden\"}");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ResponseEntity<Iterable<AddressDTO>> getAll(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		if(hasRole("ROLE_ADMIN")) {
			Iterable<Address> dbAddress = addressRepo.findAll();
			List<AddressDTO> addressDTOS= new ArrayList<>();
			dbAddress.forEach(address -> addressDTOS.add(new AddressDTO(address)));
			return ResponseEntity.ok(addressDTOS);
		} else {
			Player userAuthenticated = userRepository.findByUsernameIgnoreCase(authentication.getName());
			Iterable<Address> dbAddress = addressRepo.findAllByPlayer(userAuthenticated);
			List<AddressDTO> addressDTOS= new ArrayList<>();
			dbAddress.forEach(address -> addressDTOS.add(new AddressDTO(address)));
			return ResponseEntity.ok(addressDTOS);
		}
	}
	
	@PostMapping
	public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO,
										  @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		String currentUser = authentication.getName();
		Player user = userRepository.findByUsernameIgnoreCase(currentUser);
		addressDTO.setUser(new PlayerDTO(user));
		Address address = new Address(addressDTO);
		try {
			addressRepo.save(address);
			return new ResponseEntity<>(new AddressDTO(address), HttpStatus.CREATED);
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
	public ResponseEntity update(@PathVariable int id, @RequestBody Address formAddress,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
			if (dbAddress.isPresent()) {
				Address updatedAddress = updateAddress(formAddress, dbAddress.get());
				updatedAddress = addressRepo.save(updatedAddress);
				AddressDTO addressDTO = new AddressDTO(updatedAddress);
				return ResponseEntity.ok(addressDTO);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.headers(headers)
					.body( "{\"message\": \"Forbidden\"}");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id,
										 @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.valueOf("application/json"));
		if (isOwner(authentication, id) || hasRole("ROLE_ADMIN")) {
			if(dbAddress.isPresent()) {
				try {
					addressRepo.delete(dbAddress.get());
					return new ResponseEntity<>(jsonService.successBody(true), responseHeader, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
				}
			} else {
				return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
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
		try {
			String currentUser = authentication.getName();
			Player userAuthenticated = userRepository.findByUsernameIgnoreCase(currentUser);
			Player userOwner = addressRepo.findById(id).get().getPlayer();
			return userOwner.equals(userAuthenticated);
		} catch (Exception e) {
			return false;
		}
	}
}
