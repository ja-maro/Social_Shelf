package com.quest.etna.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.quest.etna.model.DTO.MessageDTO;

public interface IMessageService {

	 List<MessageDTO> getByEventId(Authentication authentication, int eventId);
	 MessageDTO create(MessageDTO messageDTO, Authentication authentication, int eventId);
}
