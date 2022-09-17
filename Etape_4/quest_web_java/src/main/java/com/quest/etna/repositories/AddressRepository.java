package com.quest.etna.repositories;

import com.quest.etna.model.Address;
import com.quest.etna.model.AddressDTO;
import com.quest.etna.model.User;

import com.quest.etna.model.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    public Iterable<Address> findAllByUser(User user);
}
