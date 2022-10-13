package com.quest.etna.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {

 List<Message> findByEventIdOrderByCreationDateDesc(int eventId);

}
