package com.quest.etna.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.PlayerDTO;

public interface IPlayerService {
	
	public List<Player> getAll();
	public List<PlayerDTO> getAllDTO();
	public List<PlayerDTO> getParticipantsByEventId(Authentication auth, Integer id);
	public PlayerDTO getById(Integer id);
	public Player create(Player entity);
	public ResponseEntity<PlayerDTO> update (Integer id, Authentication auth, PlayerDTO formPlayer);
	public ResponseEntity<String> delete(Integer id, Authentication auth);
	public ResponseEntity<String> disable(Integer id, Authentication auth);
	public boolean hasRole (String roleName);
	public boolean isUser(Authentication authentication, int id);
	
	public Player getAuthenticatedPlayer(Authentication auth);

}
