package com.quest.etna.service;

import com.quest.etna.model.DTO.EventDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IEventService {
	List<EventDTO> getAll();
	List<EventDTO> getAllPastByPlayer(Authentication authentication);
	List<EventDTO> getAllFutureByPlayer(Authentication authentication);
	List<EventDTO> getAllFutureParticipationPossible(Authentication authentication);
	EventDTO getById(Integer id);
	EventDTO create(EventDTO eventDTO, Authentication authentication);
	EventDTO update (EventDTO eventDTO, Integer id, Authentication authentication);
	String delete(Integer id, Authentication authentication);
	EventDTO join(Integer id, Authentication authentication);
	EventDTO quit (Integer id, Authentication authentication);
}
