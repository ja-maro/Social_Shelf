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
	
	List<Event> findByOrganizerId(int organizerId);
	List<Event> findByOrganizerIdAndStartDateGreaterThanEqualOrderByStartDate(int organizerId, Instant instant);
	List<Event> findByParticipantsIdAndStartDateGreaterThanEqualOrderByStartDate(int participantsId, Instant instant);

//	List<Event> findByStartDateAfterAndOrganizerId(Instant instant, int organizerId);
//	List<Event> findByStartDateAfterAndParticipantsId(Instant instant, int participantsId);
//	
//	List<Event> findByOrganizerId(int playerId);
//	List<Event> FindByParticipantsId(int playerId);
//	
	@Query("SELECT e FROM Event e WHERE e.organizer.id <>:playerId AND e.startDate >= CURRENT_DATE() AND NOT EXISTS (SELECT p FROM e.participants p WHERE p.id =:playerId) ")
	List<Event> findFutureEventPlayerCanParticipate(@Param("playerId") int playerId);
//
//	@Query("SELECT e FROM Event e WHERE e.organizer.id =:playerId AND e.startDate >= CURRENT_DATE()")
//	List<Event> findFutureByOrganizer(@Param("playerId") int playerId);
//
//	@Query("SELECT e FROM Event e WHERE e.participants.id =:playerId AND e.startDate >= CURRENT_DATE()")
//	List<Event> findFutureByParticipant(@Param("playerId") int playerId);
	
}
