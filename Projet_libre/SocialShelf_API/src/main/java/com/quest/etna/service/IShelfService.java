package com.quest.etna.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.quest.etna.model.DTO.GameDTO;

public interface IShelfService {

	List<GameDTO> getAll(Authentication auth);
	GameDTO add(int gameId, Authentication auth);
	Boolean remove(Integer id);

}
