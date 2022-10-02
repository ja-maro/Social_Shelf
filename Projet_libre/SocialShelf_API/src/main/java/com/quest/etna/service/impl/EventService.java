package com.quest.etna.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.quest.etna.model.Event;
import com.quest.etna.model.DTO.EventDTO;
import com.quest.etna.repositories.EventRepository;
import com.quest.etna.service.IEventService;

public class EventService implements IEventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public EventDTO create(Event event) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
