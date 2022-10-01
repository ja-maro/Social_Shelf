package com.quest.etna.controller;

import com.quest.etna.model.GameDTO;
import com.quest.etna.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    IGameService iGameService;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getAll() {
        return ResponseEntity.ok(iGameService.getAll());
    }

    @PostMapping
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO gameDTO,
                                          @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        GameDTO newGame = iGameService.create(gameDTO, authentication);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }
}
