package com.quest.etna.service.impl;

import com.quest.etna.model.Game;
import com.quest.etna.model.GameDTO;
import com.quest.etna.repositories.GameRepository;
import com.quest.etna.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService implements IGameService {
    @Autowired
    GameRepository gameRepository;

    public List<GameDTO> getAll() {
        Iterable<Game> games = gameRepository.findAll();
        List<GameDTO> gameDTOS = new ArrayList<>();
        games.forEach(game -> gameDTOS.add(new GameDTO(game)));
        return gameDTOS;
    }

    public GameDTO create(GameDTO gameDTO, Authentication authentication) {
        Game newGame = new Game(gameDTO);
        try {
            gameRepository.save(newGame);
            return new GameDTO(newGame);
        } catch (DataIntegrityViolationException e) {
            if (newGame.getName() == null ||
                    newGame.getDescription() == null ||
                    newGame.getPublisher() == null ||
                    newGame.getMinPlayer() == 0 ||
                    newGame.getMaxPlayer() == 0 ||
                    newGame.getAverageDuration() == 0 ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
