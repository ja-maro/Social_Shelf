package com.quest.etna.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.Event;
import com.quest.etna.model.Message;
import com.quest.etna.model.Player;
import com.quest.etna.model.DTO.MessageDTO;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.repositories.EventRepository;
import com.quest.etna.repositories.MessageRepository;
import com.quest.etna.service.IMessageService;

@Service
public class MessageService implements IMessageService {
	
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	EventRepository eventRepository;
	@Autowired
	PlayerService playerService;

	@Override
	public List<MessageDTO> getByEventId(Authentication authentication, int eventId) {
		int playerId = playerService.getAuthenticatedPlayer(authentication).getId();
		if (playerService.isParticipantOrOrganizer(playerId, eventId)) {
			System.out.println("\t####### service");
			List<MessageDTO> results = new ArrayList<>();
			Iterable<Message> messages = messageRepository.findByEventIdOrderByCreationDateDesc(eventId);
			messages.forEach(message -> {
				MessageDTO messageDTO = new MessageDTO(message);
				results.add(messageDTO);
				System.out.println(messageDTO.getContent());
			});
			return results;			
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	@Override
	public MessageDTO create(MessageDTO messageDTO, Authentication authentication, int eventId) {
		Player player = playerService.getAuthenticatedPlayer(authentication);
		
		messageDTO.setAuthor(new PlayerDTO(player));
		if (playerService.isParticipantOrOrganizer(player.getId(), eventId)) {
			Optional<Event> eventOptional = eventRepository.findById(eventId);
			if (eventOptional.isPresent()) {
				Event event = eventOptional.get();
				Message newMessage = new Message(messageDTO);
				newMessage.setEvent(event);
				newMessage.setAuthor(player);
				try {
					messageRepository.save(newMessage);
					return new MessageDTO(newMessage);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

}
