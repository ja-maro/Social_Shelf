package com.quest.etna.repositories;

import com.quest.etna.model.Game;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
	List<Game> findGamesByPlayersId(int playerId);
}
