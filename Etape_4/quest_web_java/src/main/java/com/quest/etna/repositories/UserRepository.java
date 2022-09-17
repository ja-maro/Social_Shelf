package com.quest.etna.repositories;

import com.quest.etna.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsernameIgnoreCase(String username);
    public boolean existsByUsernameIgnoreCase(String username);
}
