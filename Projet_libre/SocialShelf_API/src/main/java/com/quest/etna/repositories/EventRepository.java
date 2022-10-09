package com.quest.etna.repositories;

import com.quest.etna.model.Event;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	
	List<Event> findByOrganizerIdOrderByStartDate(int organizerId);
	List<Event> findByParticipantsIdOrderByStartDate(int organizerId);
	List<Event> findByOrganizerIdAndStartDateGreaterThanEqualOrderByStartDate(int organizerId, Instant instant);
	List<Event> findByParticipantsIdAndStartDateGreaterThanEqualOrderByStartDate(int participantsId, Instant instant);

	@Query("SELECT e FROM Event e WHERE e.organizer.id <>:playerId AND e.startDate >= CURRENT_DATE() AND NOT EXISTS (SELECT p FROM e.participants p WHERE p.id =:playerId) ORDER BY e.startDate")
	List<Event> findFutureEventPlayerCanParticipate(@Param("playerId") int playerId);

	
}
