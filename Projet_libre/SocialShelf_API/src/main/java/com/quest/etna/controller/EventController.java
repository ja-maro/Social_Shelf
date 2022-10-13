package com.quest.etna.controller;

import com.quest.etna.model.DTO.EventDTO;
import com.quest.etna.model.DTO.PlayerDTO;
import com.quest.etna.service.IEventService;
import com.quest.etna.service.IPlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    IEventService eventService;
    
    @Autowired
    IPlayerService playerService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }
    
    @GetMapping(value="/join")
    public ResponseEntity<List<EventDTO>> getFutureEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(eventService.getAllFutureParticipationPossible(authentication));
    }
    
    @PutMapping(value="/join/{id}")
    public ResponseEntity<EventDTO> joinEvent(@PathVariable Integer id,
                                           @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(eventService.join(id, authentication));
    }

    @PutMapping(value = "/quit/{id}")
    public ResponseEntity<EventDTO> quitEvent(@PathVariable Integer id,
                                              @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(eventService.quit(id, authentication));
    }
    
    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<EventDTO> cancelEvent(@PathVariable Integer id,
    		@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(eventService.cancel(id, authentication));
    }
    
    @GetMapping(value="/past")
    public ResponseEntity<List<EventDTO>> getPastPlayerEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(eventService.getAllPastByPlayer(authentication));
    }
    @GetMapping(value="/future")
    public ResponseEntity<List<EventDTO>> getFuturePlayerEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(eventService.getAllFutureByPlayer(authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getById(id));
    }
    
    @GetMapping("/participants/{id}")
    public ResponseEntity<List<PlayerDTO>> getParticipantsById(@CurrentSecurityContext(expression = "authentication") Authentication authentication, @PathVariable Integer id) {
        return ResponseEntity.ok(playerService.getParticipantsByEventId(authentication, id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO eventDTO,  @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        EventDTO newEvent = eventService.create(eventDTO, authentication);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Integer id,
                                           @RequestBody EventDTO eventDTO,
                                           @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(eventService.update(eventDTO, id, authentication));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id,
                                         @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(eventService.delete(id, authentication));
    }
}
