package com.quest.etna.repositories;

import com.quest.etna.model.Event;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	
	List<Event> findByOrganizerIdOrderByStartDate(int organizerId);
	List<Event> findByParticipantsIdOrderByStartDate(int organizerId);
	List<Event> findByOrganizerIdAndStartDateGreaterThanEqualOrderByStartDate(int organizerId, Instant instant);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO participants (player_id, event_id) VALUES (:playerId, :eventId);",
			nativeQuery = true)
	void joinEvent(@Param("playerId")int playerId, @Param("eventId") int eventId);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM participants WHERE player_id = :playerId AND event_id = :eventId",
	nativeQuery = true)
	void quitEvent(@Param("playerId")int playerId, @Param("eventId") int eventId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Event e SET e.cancelDate = CURRENT_DATE() WHERE e.id=:eventId")
	void cancelEvent(@Param("eventId") int eventId); 
	
	@Query("SELECT e FROM Event e "
			+ "WHERE (e.organizer.id =:playerId "
			+ "OR EXISTS (SELECT p FROM e.participants p WHERE p.id =:playerId)) "
			+ "AND e.startDate < :date "
//			+ "AND e.cancelDate IS NULL "
			+ "ORDER BY e.startDate")
	List<Event> findPastPlayerEvents(@Param("playerId") int playerId, @Param("date") Instant instant); 
	
	@Query("SELECT e FROM Event e "
			+ "WHERE (e.organizer.id =:playerId "
			+ "OR EXISTS (SELECT p FROM e.participants p WHERE p.id =:playerId)) "
			+ "AND e.startDate >= :date "
//			+ "AND e.cancelDate IS NULL "
			+ "ORDER BY e.startDate")
	List<Event> findFuturePlayerEvents(@Param("playerId") int playerId, @Param("date") Instant instant); 

	@Query("SELECT e FROM Event e "
			+ "WHERE e.organizer.id <>:playerId "
			+ "AND e.startDate >= :date "
			+ "AND e.cancelDate IS NULL "
			+ "AND NOT EXISTS (SELECT p FROM e.participants p WHERE p.id =:playerId) "
			+ "AND e.maxPlayer > (SELECT COUNT(p) FROM e.participants p) "
			+ "ORDER BY e.startDate")
	List<Event> findFutureEventPlayerCanParticipate(@Param("playerId") int playerId, @Param("date") Instant instant);

	
}
