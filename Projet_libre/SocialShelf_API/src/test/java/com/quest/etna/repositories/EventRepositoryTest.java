package com.quest.etna.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.quest.etna.model.Event;

@Sql(scripts = "/import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
public class EventRepositoryTest {
	
	@Autowired
	EventRepository eventRepository;

	@Test
	public void findByOrganizerId_When1_ShouldReturn2Events() {
		 List<Event> result = eventRepository.findByOrganizerIdOrderByStartDate(1);
	        
	        assertEquals(2, result.size());
	        assertTrue(result.stream()
	                .map(Event::getId)
	                .allMatch(id -> Arrays.asList(2, 3).contains(id)));
	}	
	
	@Test
	public void findByParticipantsId_When2_ShouldReturn2Events() {
		List<Event> result = eventRepository.findByParticipantsIdOrderByStartDate(2);
		
		assertEquals(2, result.size());
		assertTrue(result.stream()
				.map(Event::getId)
				.allMatch(id -> Arrays.asList(2, 3).contains(id)));
	}

	
	@Test
    public void findByOrganizerIdAndStartDateAfter_whenId1AndOctober1st_shouldReturnUnoChezMoi() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu/MM/dd HH:mm" , Locale.FRENCH );
		Instant now = LocalDateTime.parse( "2022/10/01 18:00" , formatter ).atZone(ZoneId.of( "Europe/Paris" )).toInstant() ;
		
        List<Event> result = eventRepository.findByOrganizerIdAndStartDateGreaterThanEqualOrderByStartDate(1, now);
        
        assertEquals(1, result.size());
        assertEquals("Uno chez moi", result.get(0).getTitle());
        assertEquals(9, result.get(0).getMaxPlayer());
    }
	
	@Test
	public void findFuturePlayerEvents_whenId2AndOctober1st_shouldReturn4() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu/MM/dd HH:mm" , Locale.FRENCH );
		Instant now = LocalDateTime.parse( "2022/10/01 18:00" , formatter ).atZone(ZoneId.of( "Europe/Paris" )).toInstant() ;
		
		List<Event> result = eventRepository.findFuturePlayerEvents(2, now);
		
		assertEquals(4, result.size());
		assertEquals("Uno chez moi", result.get(0).getTitle());
		assertEquals("Tournoi 7 Wonders", result.get(1).getTitle());
		assertEquals("Tournoi 7 Wonders annulé", result.get(2).getTitle());
		assertEquals("Aeon's End à Paris", result.get(3).getTitle());
		assertEquals(2, result.get(0).getId());
		assertEquals(4, result.get(1).getId());
		assertEquals(5, result.get(2).getId());
		assertEquals(1, result.get(3).getId());
	}
	
	@Test
	public void findFutureEventPlayerCanParticipate_whenId1AndOctober1st_shouldReturnTournoi7Wonders() {		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu/MM/dd HH:mm" , Locale.FRENCH );
		Instant now = LocalDateTime.parse( "2022/10/01 18:00" , formatter ).atZone(ZoneId.of( "Europe/Paris" )).toInstant() ;
		
		List<Event> result = eventRepository.findFutureEventPlayerCanParticipate(1, now);
		
		assertEquals(1, result.size());
		assertEquals("Tournoi 7 Wonders", result.get(0).getTitle());
		assertEquals(4, result.get(0).getId());
	}
	
	@Test
	public void cancelEvent_ShouldAddCancelDate() {		
				
		Optional<Event> result = eventRepository.findById(1);
		assertTrue(result.isPresent());
		assertNull(result.get().getCancelDate());
		
		eventRepository.cancelEvent(1);
		
		Optional<Event> cancelled = eventRepository.findById(1);
		assertTrue(cancelled.isPresent());
		assertNotNull(cancelled.get().getCancelDate());
	}
}
