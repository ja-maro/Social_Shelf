package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    public Iterable<Address> findAllByUser(User user);
}
