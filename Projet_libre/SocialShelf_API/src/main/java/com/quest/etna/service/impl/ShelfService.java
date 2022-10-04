package com.quest.etna.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.quest.etna.model.Address;
import com.quest.etna.model.Game;
import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.AddressDTO;
import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.repositories.GameRepository;
import com.quest.etna.repositories.GameTypeRepository;
import com.quest.etna.service.IShelfService;

@Service
public class ShelfService implements IShelfService {

	@Autowired
	GameRepository gameRepository;
	@Autowired
	PlayerService playerService;
	@Autowired
	GameTypeRepository gameTypeRepository;

	@Override
	public GameDTO Add(int gameId, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean remove(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameDTO> getAll(Authentication auth) {
		Player player = playerService.getAuthenticatedPlayer(auth);
		Iterable<Game> dbShelf = gameRepository.findByOwnersId(player.getId());
		List<GameDTO> GameDTOs = new ArrayList<>();
		dbShelf.forEach(game -> GameDTOs.add(new GameDTO(game)));
		return GameDTOs;
	}

}
