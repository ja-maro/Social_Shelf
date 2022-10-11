package com.quest.etna.controller;

import com.quest.etna.model.DTO.EventDTO;
import com.quest.etna.service.IEventService;
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
    IEventService iEventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(iEventService.getAll());
    }
    
    @GetMapping(value="/join")
    public ResponseEntity<List<EventDTO>> getFutureEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(iEventService.getAllFutureParticipationPossible(authentication));
    }
    
    @PutMapping(value="/join/{id}")
    public ResponseEntity<EventDTO> joinEvent(@PathVariable Integer id,
                                           @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(iEventService.join(id, authentication));
    }
    
    @GetMapping(value="/past")
    public ResponseEntity<List<EventDTO>> getPastPlayerEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(iEventService.getAllPastByPlayer(authentication));
    }
    @GetMapping(value="/future")
    public ResponseEntity<List<EventDTO>> getFuturePlayerEvents(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
    	return ResponseEntity.ok(iEventService.getAllFutureByPlayer(authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(iEventService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO eventDTO,  @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        EventDTO newEvent = iEventService.create(eventDTO, authentication);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Integer id,
                                           @RequestBody EventDTO eventDTO,
                                           @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(iEventService.update(eventDTO, id, authentication));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id,
                                         @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(iEventService.delete(id, authentication));
    }
}
