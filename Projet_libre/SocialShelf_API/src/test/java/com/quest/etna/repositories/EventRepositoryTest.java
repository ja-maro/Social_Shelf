package com.quest.etna.repositories;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
		Instant instant = LocalDateTime.parse( "2022/10/01 18:00" , formatter ).atZone(ZoneId.of( "Europe/Paris" )).toInstant() ;
		
        List<Event> result = eventRepository.findByOrganizerIdAndStartDateGreaterThanEqualOrderByStartDate(1, instant);
        
        assertEquals(1, result.size());
        assertEquals("Uno chez moi", result.get(0).getTitle());
        assertEquals(9, result.get(0).getMaxPlayer());
    }
	
	@Test
	public void findByParticipantsIdAndStartDateGreaterThanEqualOrderByStartDate_whenId2AndOctober1st_shouldReturnUnoChezMoi() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu/MM/dd HH:mm" , Locale.FRENCH );
		Instant instant = LocalDateTime.parse( "2022/10/01 18:00" , formatter ).atZone(ZoneId.of( "Europe/Paris" )).toInstant() ;
		
		List<Event> result = eventRepository.findByParticipantsIdAndStartDateGreaterThanEqualOrderByStartDate(2, instant);
		
		assertEquals(1, result.size());
		assertEquals("Uno chez moi", result.get(0).getTitle());
		assertEquals(2, result.get(0).getId());
	}
	
	@Test
	public void findFutureEventPlayerCanParticipate_whenId1_shouldReturnournoi7Wonders() {		
		List<Event> result = eventRepository.findFutureEventPlayerCanParticipate(1);
		
		assertEquals(1, result.size());
		assertEquals("Tournoi 7 Wonders", result.get(0).getTitle());
		assertEquals(4, result.get(0).getId());
	}
}
