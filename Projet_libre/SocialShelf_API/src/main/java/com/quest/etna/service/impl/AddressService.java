package com.quest.etna.service.impl;

import com.quest.etna.model.Address;
import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.AddressDTO;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.PlayerRepository;
import com.quest.etna.service.IAddressService;
import com.quest.etna.service.JsonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {
	
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PlayerService playerService;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    private JsonService jsonService;

    @Override
    public ResponseEntity getById(Integer id, Authentication authentication) {
        Optional<Address> dbAddress = addressRepository.findById(id);
        System.out.print("is user ? " + playerService.isUser(authentication, id));
        if (dbAddress.isPresent()) {
            if (playerService.isUser(authentication, id) || playerService.hasRole("ROLE_ADMIN")) {
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

    @Override
    public ResponseEntity<Iterable<AddressDTO>> getAll(Authentication authentication) {
        if(playerService.hasRole("ROLE_ADMIN")) {
            Iterable<Address> dbAddress = addressRepository.findAll();
            List<AddressDTO> addressDTOS= new ArrayList<>();
            dbAddress.forEach(address -> addressDTOS.add(new AddressDTO(address)));
            return ResponseEntity.ok(addressDTOS);
        } else {
            Player userAuthenticated = playerRepository.findByUsernameIgnoreCase(authentication.getName());
            Iterable<Address> dbAddress = addressRepository.findAllByPlayer(userAuthenticated);
            List<AddressDTO> addressDTOS= new ArrayList<>();
            dbAddress.forEach(address -> addressDTOS.add(new AddressDTO(address)));
            return ResponseEntity.ok(addressDTOS);
        }
    }

    @Override
    public ResponseEntity<AddressDTO> create (AddressDTO addressDTO, Authentication authentication) {
        String currentUser = authentication.getName();
        Player player = playerRepository.findByUsernameIgnoreCase(currentUser);
        addressDTO.setPlayer(new PlayerDTO(player));
        Address address = new Address(addressDTO);
        try {
            addressRepository.save(address);
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

    @Override
    public ResponseEntity update (Integer id, Address formAddress, Authentication authentication) {
        Optional<Address> dbAddress = addressRepository.findById(id);
        if (playerService.isUser(authentication, id) || playerService.hasRole("ROLE_ADMIN")) {
            if (dbAddress.isPresent()) {
                Address updatedAddress = updateAddress(formAddress, dbAddress.get());
                updatedAddress = addressRepository.save(updatedAddress);
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

    @Override
    public ResponseEntity<String> delete (Integer id, Authentication authentication) {
        Optional<Address> dbAddress = addressRepository.findById(id);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.valueOf("application/json"));
        if (playerService.isUser(authentication, id) || playerService.hasRole("ROLE_ADMIN")) {
            if(dbAddress.isPresent()) {
                try {
                    addressRepository.delete(dbAddress.get());
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

}
