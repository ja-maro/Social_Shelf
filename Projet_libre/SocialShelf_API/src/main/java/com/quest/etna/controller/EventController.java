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

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(iEventService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO eventDTO) {
        EventDTO newEvent = iEventService.create(eventDTO);
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
