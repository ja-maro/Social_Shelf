package com.quest.etna.repositories;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
}
