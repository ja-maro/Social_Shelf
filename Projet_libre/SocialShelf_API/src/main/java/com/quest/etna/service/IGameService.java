package com.quest.etna.service;

import com.quest.etna.model.GameDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IGameService {
    List<GameDTO> getAll();
    GameDTO create(GameDTO gameDTO, Authentication authentication);
    GameDTO getById(Integer id);
    GameDTO update(GameDTO gameDTO, Integer id);
    Boolean delete(Integer id);
}
