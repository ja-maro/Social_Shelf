package com.quest.etna.service.impl;

import com.quest.etna.model.Game;
import com.quest.etna.model.DTO.GameDTO;
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
import java.util.Optional;

@Service
public class GameService implements IGameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerService playerService;

    private static final String ADMIN = "ROLE_ADMIN";
    private static final String FORBIDDEN = "{\"message\": \"Forbidden\"}";

    public List<GameDTO> getAll() {
        Iterable<Game> games = gameRepository.findAll();
        List<GameDTO> gameDTOS = new ArrayList<>();
        games.forEach(game -> gameDTOS.add(new GameDTO(game)));
        return gameDTOS;
    }

    public GameDTO getById(Integer id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isPresent()) {
            return new GameDTO(gameOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public GameDTO create(GameDTO gameDTO, Authentication authentication) {
        Game newGame = new Game(gameDTO);
        if (playerService.hasRole(ADMIN)) {
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
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN);
        }
    }

    public GameDTO update(GameDTO gameDTO, Integer id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isPresent()) {
            if (playerService.hasRole(ADMIN)) {
                Game updatedGame = gameUpdater(gameDTO, gameOptional.get());
                gameRepository.save(updatedGame);
                return new GameDTO(updatedGame);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Game gameUpdater(GameDTO updateGameDTO, Game toChangeGameDTO) {
        if (updateGameDTO.getName() != null)
            toChangeGameDTO.setName(updateGameDTO.getName());
        if (updateGameDTO.getPublisher() != null)
            toChangeGameDTO.setPublisher(updateGameDTO.getPublisher());
        if (updateGameDTO.getDescription() != null)
            toChangeGameDTO.setDescription(updateGameDTO.getDescription());
        if (updateGameDTO.getMinPlayer() != null)
            toChangeGameDTO.setMinPlayer(updateGameDTO.getMinPlayer());
        if (updateGameDTO.getMaxPlayer() != null)
            toChangeGameDTO.setMaxPlayer(updateGameDTO.getMaxPlayer());
        if (updateGameDTO.getAverageDuration() != null) {
            toChangeGameDTO.setAverageDuration(updateGameDTO.getAverageDuration());
        }
        return toChangeGameDTO;
    }

    public Boolean delete(Integer id) {
        Optional<Game> game = gameRepository.findById(id);
        if (playerService.hasRole(ADMIN)) {
            if (game.isPresent()) {
                try {
                    gameRepository.delete(game.get());
                    return true;
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN);
        }
        return false;
    }
}
