package com.quest.etna.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
