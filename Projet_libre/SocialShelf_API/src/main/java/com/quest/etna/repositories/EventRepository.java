package com.quest.etna.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.Game;

@Repository
public interface EventRepository extends CrudRepository<Game, Integer> {

}
