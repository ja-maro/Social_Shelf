package com.quest.etna.controller;

import com.quest.etna.model.DTO.GameDTO;
import com.quest.etna.service.IGameService;
import com.quest.etna.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    JsonService jsonService;

    @GetMapping
    public ResponseEntity<List<GameDTO>> getAll() {
        return ResponseEntity.ok(iGameService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(iGameService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO gameDTO,
                                          @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        GameDTO newGame = iGameService.create(gameDTO, authentication);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> update (@RequestBody GameDTO gameDTO,
                                          @PathVariable int id) {
        GameDTO updatedGame = iGameService.update(gameDTO, id);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable int id) {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.valueOf("application/json"));
        if (Boolean.TRUE.equals(iGameService.delete(id)))
            return new ResponseEntity<>(jsonService.successBody(true),responseHeader, HttpStatus.OK);
        else return new ResponseEntity<>(jsonService.successBody(false),responseHeader, HttpStatus.OK);
    }

    @PutMapping("/{gameId}/type/{typeId}")
    public ResponseEntity<GameDTO> addType (@PathVariable Integer gameId, @PathVariable Integer typeId) {
        return new ResponseEntity<>(iGameService.addType(gameId, typeId), HttpStatus.OK);
    }
}
