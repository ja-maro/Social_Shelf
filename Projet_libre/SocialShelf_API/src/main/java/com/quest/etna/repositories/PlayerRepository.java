package com.quest.etna.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
	
    public Player findByUsernameIgnoreCase(String username);
    
    public boolean existsByUsernameIgnoreCase(String username);
    
    Set<Player> findByGamesId(int gameId);
    
    Set<Player> findByParticipatedEventsId(int eventId);
    
    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.games WHERE p.id=:idParam")
	public Player getPlayerAndShelf (@Param("idParam") int playerId);

}
