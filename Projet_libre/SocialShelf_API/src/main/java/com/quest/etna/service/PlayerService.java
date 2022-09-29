package com.quest.etna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.etna.model.player.Player;
import com.quest.etna.model.player.PlayerDTO;
import com.quest.etna.repositories.PlayerRepository;

@Service
public class PlayerService implements IModelService<Player> {

	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public List<Player> getAll() {
		return null;
	}

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
	public Player getById(Integer id) {
		Optional<Player> player = playerRepository.findById(id);
		if (player.isPresent()) {
			return player.get();
		}
		return null;
	}

	@Override
	public Player create(Player entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player update(Integer id, Player entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
