package com.quest.etna.service;

import com.quest.etna.model.Event;
import com.quest.etna.model.DTO.EventDTO;

public interface IEventService {
	public EventDTO create(Event event);
}
