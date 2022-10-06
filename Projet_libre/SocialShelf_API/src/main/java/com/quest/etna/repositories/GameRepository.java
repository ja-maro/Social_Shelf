package com.quest.etna.repositories;

import com.quest.etna.model.Game;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
	List<Game> findGamesByPlayersId(int playerId);
	@Query("SELECT g FROM Game g WHERE NOT EXISTS (SELECT p FROM g.players p WHERE p.id =:playerId)")
	List<Game> findGamesByPlayersIdNot(@Param("playerId") int playerId);
}
