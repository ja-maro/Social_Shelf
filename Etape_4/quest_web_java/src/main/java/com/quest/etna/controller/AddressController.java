package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.Address;
import com.quest.etna.repositories.AddressRepository;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepo;
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> getById(@RequestParam int id) {
		Optional<Address> dbAddress = addressRepo.findById(id);
		if (dbAddress.isPresent()) {
			return ResponseEntity.ok(dbAddress.get());
		} else {
//			return ResponseEntity.notFound();
		}
		return null;
	}
	
	@GetMapping
	public ResponseEntity<List<Address>> getAll() {
		List<Address> addresses = new ArrayList<Address>();
		Iterable<Address> dbAddress = addressRepo.findAll();
		while (dbAddress.iterator().hasNext()) {
			addresses.add(
					dbAddress.iterator().next()
					);
		} 
		return ResponseEntity.ok(addresses);
	}
	
	@PostMapping
	public ResponseEntity<Address> create(@RequestBody Address address) {
		//TODO: Add current user to address
//		address.setUser(null);
		Address savedAddress = addressRepo.save(address);
		return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
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
