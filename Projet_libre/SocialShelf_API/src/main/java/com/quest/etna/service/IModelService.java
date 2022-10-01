package com.quest.etna.service;

import com.quest.etna.model.player.PlayerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IModelService<T> {
	
	public List<T> getAll();
	public PlayerDTO getById(Integer id);
	public T create(T entity);
	public ResponseEntity<PlayerDTO> update (Integer id, Authentication auth, PlayerDTO formPlayer);
	public ResponseEntity<String> delete(Integer id, Authentication auth);
	public ResponseEntity<String> disable(Integer id, Authentication auth);
	public boolean hasRole (String roleName);
	public boolean isUser(Authentication authentication, int id);

}
