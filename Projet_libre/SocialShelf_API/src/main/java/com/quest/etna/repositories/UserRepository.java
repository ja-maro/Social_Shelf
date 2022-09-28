package com.quest.etna.repositories;

import com.quest.etna.model.player.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Player, Integer> {
    public Player findByUsernameIgnoreCase(String username);
    public boolean existsByUsernameIgnoreCase(String username);
}
