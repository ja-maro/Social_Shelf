package com.quest.etna.controller;

import java.util.List;

import com.quest.etna.model.player.PlayerDTO;
import com.quest.etna.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/player")
public class PlayerController {


	@Autowired
	private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getById(@PathVariable int id) {
        PlayerDTO playerDTO = playerService.getById(id);
        if (playerDTO != null) {
            return ResponseEntity.ok(playerDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<PlayerDTO> results = playerService.getAllDTO();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable int id, @RequestBody PlayerDTO formPlayer, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        return playerService.update(id, auth, formPlayer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        return playerService.delete(id, auth);
    }

    
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disable(@PathVariable int id, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        return playerService.disable(id, auth);
    }
}
