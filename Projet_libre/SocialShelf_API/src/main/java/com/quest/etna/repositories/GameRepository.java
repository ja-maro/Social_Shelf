package com.quest.etna.repositories;

import com.quest.etna.model.Game;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
	Set<Game> findByOwnersId(int ownerId);
}
