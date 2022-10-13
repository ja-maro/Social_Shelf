package com.quest.etna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.DTO.MessageDTO;
import com.quest.etna.service.IMessageService;
import com.quest.etna.service.impl.MessageService;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

	@Autowired
	IMessageService messageService;
	
	@GetMapping("/{eventId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByEventId(@CurrentSecurityContext(expression = "authentication") Authentication authentication, @PathVariable Integer eventId) {
        System.out.println("\t #### controller");
		return ResponseEntity.ok(messageService.getByEventId(authentication, eventId));
    }
	
	@PostMapping("/{eventId}")
    public ResponseEntity<MessageDTO> create(@RequestBody MessageDTO messageDTO,  @CurrentSecurityContext(expression = "authentication") Authentication authentication, @PathVariable Integer eventId) {
		MessageDTO newMessage = messageService.create(messageDTO, authentication, eventId);
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

}
