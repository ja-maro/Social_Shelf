package com.quest.etna.service.impl;

import com.quest.etna.model.Game;
import com.quest.etna.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.quest.etna.model.Event;
import com.quest.etna.model.DTO.EventDTO;
import com.quest.etna.repositories.EventRepository;
import com.quest.etna.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Override
	public EventDTO create(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

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
	
}
