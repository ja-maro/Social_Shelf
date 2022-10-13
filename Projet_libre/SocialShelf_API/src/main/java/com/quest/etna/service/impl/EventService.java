package com.quest.etna.service.impl;

import com.quest.etna.model.Address;
import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.model.Game;
import com.quest.etna.model.GameType;
import com.quest.etna.model.Player;
import com.quest.etna.repositories.GameRepository;
import com.quest.etna.repositories.PlayerRepository;

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

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService implements IEventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerRepository playerRepository;

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

	public EventDTO create(EventDTO eventDTO, Authentication authentication) {
		eventDTO.setOrganizer(new PlayerDTO(playerService.getAuthenticatedPlayer(authentication)));
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
		if (newEvent.getCancelDate() != null)
			dbEvent.setCancelDate(newEvent.getCancelDate());
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

	public List<EventDTO> getAllPastByPlayer(Authentication authentication) {
		Player player = playerService.getAuthenticatedPlayer(authentication);
		Iterable<Event> events = eventRepository.findPastPlayerEvents(player.getId(), Instant.now());
		List<EventDTO> eventDTOS = new ArrayList<>();
		events.forEach(event -> eventDTOS.add(new EventDTO(event)));
		return eventDTOS;
	}

	public List<EventDTO> getAllFutureByPlayer(Authentication authentication) {
		Player player = playerService.getAuthenticatedPlayer(authentication);
		Iterable<Event> events = eventRepository.findFuturePlayerEvents(player.getId(), Instant.now());
		List<EventDTO> eventDTOS = new ArrayList<>();
		events.forEach(event -> eventDTOS.add(new EventDTO(event)));
		return eventDTOS;
	}

	public List<EventDTO> getAllFutureParticipationPossible(Authentication authentication) {
		Player player = playerService.getAuthenticatedPlayer(authentication);
		Iterable<Event> events = eventRepository.findFutureEventPlayerCanParticipate(player.getId(), Instant.now());
		List<EventDTO> eventDTOS = new ArrayList<>();
		events.forEach(event -> eventDTOS.add(new EventDTO(event)));
		return eventDTOS;
	}

	public EventDTO join(Integer id, Authentication authentication) {
		Optional<Event> eventOptional= eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			Player player = playerService.getAuthenticatedPlayer(authentication);
			int eventOrganizerId = event.getOrganizer().getId();
			int playerNumber = playerRepository.findByParticipatedEventsId(id).size();

			if(playerService.isUser(authentication, eventOrganizerId)
				|| isParticipant(player.getId(), event.getId())
				|| event.getStartDate().isBefore(Instant.now())
				|| event.getMaxPlayer() == playerNumber
				) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			} else {
				eventRepository.joinEvent(player.getId(), event.getId());
				return new EventDTO(event);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Checks whether the player is one of the participants of the given event.
	 * 
	 * @param playerId Id of player
	 * @param eventId Id of Event
	 * @return 
	 */
	public boolean isParticipant(int playerId, int eventId) {
		Set<Player> participants = playerRepository.findByParticipatedEventsId(eventId);
		if (null == participants || participants.isEmpty())
			return false;
		for (Player player : participants) {
			if (player.getId() == playerId)
				return true;
		}
		return false;
	}

	public EventDTO quit (Integer id, Authentication authentication) {
		Optional<Event> eventOptional= eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			Player player = playerService.getAuthenticatedPlayer(authentication);
			if (isParticipant(player.getId(), event.getId())) {
				eventRepository.quitEvent(player.getId(), event.getId());
				return new EventDTO(eventOptional.get());
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public EventDTO cancel(Integer id, Authentication authentication) {
		Optional<Event> eventOptional= eventRepository.findById(id);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			Player player = playerService.getAuthenticatedPlayer(authentication);
			if (
//					isOrganizer(player.getId(), event.getId())
					event.getOrganizer().getId() == player.getId()
					&& null == event.getCancelDate()
					) {
				eventRepository.cancelEvent(event.getId());
				eventOptional= eventRepository.findById(id);
				return new EventDTO(eventOptional.get());
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Checks whether player is the organizer of given event.
	 * 
	 * @param playerId
	 * @param eventId
	 * @return
	 */
	public boolean isOrganizer(int playerId, int eventId) {
		Optional<Event> eventOptional= eventRepository.findById(eventId);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			if (event.getOrganizer().getId() == playerId)
				return true;
		}
		return false;
	}
	
	
	
}
