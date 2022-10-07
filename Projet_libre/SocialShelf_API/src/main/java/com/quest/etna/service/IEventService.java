package com.quest.etna.service;

import com.quest.etna.model.Event;
import com.quest.etna.model.DTO.EventDTO;

import java.util.List;

public interface IEventService {
	EventDTO create(Event event);
	List<EventDTO> getAll();
	EventDTO getById(Integer id);
	EventDTO create(EventDTO eventDTO);
}
