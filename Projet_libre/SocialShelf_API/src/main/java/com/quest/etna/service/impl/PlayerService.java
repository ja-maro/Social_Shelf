package com.quest.etna.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quest.etna.model.Event;
import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.repositories.EventRepository;
import com.quest.etna.repositories.PlayerRepository;
import com.quest.etna.service.IPlayerService;
import com.quest.etna.service.JsonService;

import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerService implements IPlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private JsonService jsonService;
	private static final String FORBIDDEN = "{\"message\": \"Forbidden\"}";

	@Override
	public List<Player> getAll() {
		return null;
	}

	@Override
	public List<PlayerDTO> getAllDTO() {
		List<PlayerDTO> results = new ArrayList<>();
		Iterable<Player> players = playerRepository.findAll();
		players.forEach(user -> {
			PlayerDTO playerDTO = new PlayerDTO(user);
			results.add(playerDTO);
		});
		return results;
	}

	@Override
	public PlayerDTO getById(Integer id) {
		Optional<Player> dbPlayer = playerRepository.findById(id);
		if (dbPlayer.isPresent()) {
			Player player = dbPlayer.get();
			return new PlayerDTO(player);
		} else {
			return null;
		}
	}

	@Override
	public Player create(Player player) {
		return playerRepository.save(player);
	}

	@Override
	public ResponseEntity<PlayerDTO> update(Integer id, Authentication auth, PlayerDTO formPlayer) {
		Optional<Player> dbPlayer = playerRepository.findById(id);
		if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
			if (dbPlayer.isPresent()) {
				Player updatedPlayer = updatePlayer(formPlayer, dbPlayer.get());
				if (hasRole("ROLE_ADMIN")) {
					updatedPlayer = updateRole(formPlayer, updatedPlayer);
				}
				updatedPlayer = playerRepository.save(updatedPlayer);
				PlayerDTO playerDTO = new PlayerDTO(updatedPlayer);
				return ResponseEntity.ok(playerDTO);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN);
		}
	}

	@Override
	public ResponseEntity<String> delete(Integer id, Authentication auth) {
		Optional<Player> dbUser = playerRepository.findById(id);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.valueOf("application/json"));
		if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
			if(dbUser.isPresent()) {
				try {
					playerRepository.delete(dbUser.get());
					return new ResponseEntity<>(jsonService.successBody(true), responseHeader, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
				}
			} else {
				return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
	}

	@Override
	public ResponseEntity<String> disable(Integer id, Authentication auth) {
		Optional<Player> dbUser = playerRepository.findById(id);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(MediaType.valueOf("application/json"));
		if(dbUser.isPresent()) {
			if (hasRole("ROLE_ADMIN")) {
				Player userDisable = dbUser.get();
				userDisable.setDisableDate(Instant.now());
				playerRepository.save(userDisable);
				return new ResponseEntity<>(jsonService.successBody(true), responseHeader, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.NOT_FOUND);
		}
	}


	private Player updatePlayer(PlayerDTO formPlayer, Player player) {
		if (null != formPlayer.getUsername()) {
			player.setUsername(formPlayer.getUsername());
		}
		if (null != formPlayer.getEmail()) {
			player.setEmail(formPlayer.getEmail());
		}
		return player;
	}

	private Player updateRole(PlayerDTO formPlayer, Player player) {
		if (null != formPlayer.getRole()) {
			player.setRole(formPlayer.getRole());
		}
		return player;
	}

	@Override
	public boolean hasRole (String roleName) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
	
	@Override
	public boolean isUser(Authentication authentication, int id) {
		try {
			Player userAuthenticated = getAuthenticatedPlayer(authentication);		
			Player userToModify = playerRepository.findById(id).get();
			System.out.print("auth : " + userAuthenticated.getUsername() + "; modification : " + userToModify.getUsername());
			return (userToModify.getId() == userAuthenticated.getId());
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Player getAuthenticatedPlayer(Authentication auth) {
		return playerRepository.findByUsernameIgnoreCase(auth.getName());
	}

	@Override
	public List<PlayerDTO> getParticipantsByEventId(Authentication auth, Integer id) {
		int playerId = getAuthenticatedPlayer(auth).getId();
		if (isParticipantOrOrganizer(playerId, id)) {
			List<PlayerDTO> results = new ArrayList<>();
			Iterable<Player> players = playerRepository.findByParticipatedEventsId(id);
			players.forEach(user -> {
				PlayerDTO playerDTO = new PlayerDTO(user);
				results.add(playerDTO);
			});
			return results;			
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN);
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
	
	/**
	 * Checks whether player is a participant or the organizer of a given event.
	 * 
	 * @param playerId
	 * @param eventId
	 * @return
	 */
	public boolean isParticipantOrOrganizer(int playerId, int eventId) {
		return isParticipant(playerId, eventId) || isOrganizer(playerId, eventId);
	}
}
