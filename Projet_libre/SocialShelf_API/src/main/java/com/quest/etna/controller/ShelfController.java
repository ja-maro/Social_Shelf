package com.quest.etna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.service.IShelfService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/shelf")
public class ShelfController {

	@Autowired
	private IShelfService shelfService;

	@GetMapping
	public ResponseEntity<List<GameDTO>> getAll(
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		List<GameDTO> shelvedGames = shelfService.getAll(authentication);
		return ResponseEntity.ok(shelvedGames);
	}

	@PutMapping("/{gameId}")
	public ResponseEntity<GameDTO> add(@PathVariable Integer gameId,
			@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
		GameDTO addedGame = shelfService.add(gameId, authentication);
		return new ResponseEntity<>(addedGame, HttpStatus.OK);
	}

}
