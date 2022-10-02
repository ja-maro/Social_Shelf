package com.quest.etna.repositories;

import com.quest.etna.model.GameType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends CrudRepository<GameType, Integer> {
}
