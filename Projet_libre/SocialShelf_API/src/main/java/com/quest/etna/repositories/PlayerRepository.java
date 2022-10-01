package com.quest.etna.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    public Player findByUsernameIgnoreCase(String username);
    public boolean existsByUsernameIgnoreCase(String username);
}
