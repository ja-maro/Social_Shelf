package com.quest.etna.service.impl;

import com.quest.etna.model.Address;
import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.model.Game;
import com.quest.etna.model.Player;
import com.quest.etna.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.quest.etna.model.Event;
import com.quest.etna.model.DTO.EventDTO;
import com.quest.etna.repositories.EventRepository;
import com.quest.etna.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private PlayerService playerService;

	public List<EventDTO> getAll() {
		Iterable<Event> events = eventRepository.findAll();
		List<EventDTO> eventDTOS = new ArrayList<>();
		events.forEach(event -> eventDTOS.add(new EventDTO(event)));
		return eventDTOS;
	}

	public EventDTO getById(Integer id) {
		Optional<Event> eventOptional = eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			return new EventDTO(event);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public EventDTO create(EventDTO eventDTO) {
		Integer gameId = eventDTO.getGame().getId();
		Optional<Game> gameOptional = gameRepository.findById(gameId);
		if (gameOptional.isPresent()) {
			Game game = gameOptional.get();
			Event newEvent = new Event(eventDTO);
			newEvent.setGame(game);
			try {
				eventRepository.save(newEvent);
				return new EventDTO(newEvent);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public EventDTO update (EventDTO eventDTO, Integer id, Authentication authentication) {
		Optional<Event> eventOptional= eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			int eventOrganizerId = event.getOrganizer().getId();
			if (playerService.hasRole("ROLE_ADMIN") || playerService.isUser(authentication, eventOrganizerId)) {
				Event eventUpdated = eventUpdater(eventDTO, event);
				eventRepository.save(eventUpdated);
				return new EventDTO(eventUpdated);
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	private Event eventUpdater (EventDTO newEvent, Event dbEvent) {
		if (newEvent.getTitle() != null)
			dbEvent.setTitle(newEvent.getTitle());
		if (newEvent.getPitch() != null)
			dbEvent.setPitch(newEvent.getPitch());
		if (newEvent.getMinPlayer() != 0)
			dbEvent.setMinPlayer(newEvent.getMinPlayer());
		if (newEvent.getMaxPlayer() != 0)
			dbEvent.setMaxPlayer(newEvent.getMaxPlayer());
		if (newEvent.getDuration() != 0)
			dbEvent.setDuration(newEvent.getDuration());
		if (newEvent.getStartDate() != null)
			dbEvent.setStartDate(newEvent.getStartDate());
		if (newEvent.getCancellationDate() != null)
			dbEvent.setCancellationDate(newEvent.getCancellationDate());
		if (newEvent.getPlace() != null)
			dbEvent.setPlace(new Address(newEvent.getPlace()));
		if (newEvent.getGame() != null) {
			Optional<Game> gameOptional = gameRepository.findById(newEvent.getGame().getId());
			if (gameOptional.isPresent()) {
				Game game = gameOptional.get();
				dbEvent.setGame(game);
			}
		}
		return dbEvent;
	}

	public String delete(Integer id, Authentication authentication) {
		Optional<Event> eventOptional = eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			int organizer = eventOptional.get().getOrganizer().getId();
			System.out.print("-------------------------- id : " + organizer);
			if (playerService.hasRole("ROLE_ADMIN") || playerService.isUser(authentication, organizer)) {
				try {
					eventRepository.delete(eventOptional.get());
					return "{\"Message\": \"Success\"}";
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
				}
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
}
