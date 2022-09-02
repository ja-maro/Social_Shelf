package com.quest.etna.repositories;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
