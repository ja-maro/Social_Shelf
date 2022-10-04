package com.quest.etna.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.Game;
import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.repositories.GameRepository;
import com.quest.etna.repositories.PlayerRepository;
import com.quest.etna.service.IShelfService;

@Service
public class ShelfService implements IShelfService {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private PlayerRepository playerRepository;

	private static final String FORBIDDEN = "{\"message\": \"Forbidden\"}";
	private static final String NOT_FOUND = "{\"message\": \"Not Found\"}";
	private static final String CONFLICT = "{\"message\": \"Conflict\"}";

	@Override
	public GameDTO add(int gameId, Authentication auth) {		
		Optional<Game> gameOptional = gameRepository.findById(gameId);
		if (gameOptional.isPresent()) {
			Game game = gameOptional.get();
			Player player = playerService.getAuthenticatedPlayer(auth);
			player = playerRepository.getPlayerAndShelf(player.getId());
			try {
				player.addGame(game);
				playerRepository.save(player);
				return new GameDTO(game);
			} catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
			}					
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
		}
	}


	@Override
	public Boolean remove(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameDTO> getAll(Authentication auth) {
		Player player = playerService.getAuthenticatedPlayer(auth);
		Iterable<Game> dbShelf = gameRepository.findGamesByPlayersId(player.getId());
		List<GameDTO> gameDTOs = new ArrayList<>();
		dbShelf.forEach(game -> gameDTOs.add(new GameDTO(game)));
		return gameDTOs;
	}

}
