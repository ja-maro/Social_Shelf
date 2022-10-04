package com.quest.etna.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.repositories.PlayerRepository;
import com.quest.etna.service.IPlayerService;
import com.quest.etna.service.JsonService;

import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerService implements IPlayerService {

	@Autowired
	private PlayerRepository playerRepository;
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
			return userToModify.equals(userAuthenticated);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Player getAuthenticatedPlayer(Authentication auth) {
		Player userAuthenticated = playerRepository.findByUsernameIgnoreCase(auth.getName());
		return userAuthenticated;
	}
}
