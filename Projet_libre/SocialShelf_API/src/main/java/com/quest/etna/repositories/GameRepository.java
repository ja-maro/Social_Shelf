package com.quest.etna.repositories;

import com.quest.etna.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {

}
