package com.quest.etna.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.quest.etna.model.Address;
import com.quest.etna.model.DTO.AddressDTO;

public interface IAddressService {

	public ResponseEntity getById(Integer id, Authentication authentication);
	public ResponseEntity<Iterable<AddressDTO>> getAll(Authentication authentication);
	public ResponseEntity<AddressDTO> create (AddressDTO addressDTO, Authentication authentication);
	public ResponseEntity update (Integer id, Address formAddress, Authentication authentication);
	public ResponseEntity<String> delete (Integer id, Authentication authentication);
}
