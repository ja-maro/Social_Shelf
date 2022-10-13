package com.quest.etna.model.DTO;

import java.time.Instant;

import com.quest.etna.model.Message;

public class MessageDTO {

	private int id;
	private String content;
	private EventDTO event;
	private PlayerDTO author;
	private Instant creationDate;
	
	public MessageDTO() {
		super();
	}
	
	
	public MessageDTO(Message message) {
		super();
		this.id = message.getId();
		this.content = message.getContent();
		this.event = new EventDTO(message.getEvent());
		this.author = new PlayerDTO (message.getAuthor());
		this.creationDate = message.getCreationDate();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public EventDTO getEvent() {
		return event;
	}
	public void setEvent(EventDTO event) {
		this.event = event;
	}
	public PlayerDTO getAuthor() {
		return author;
	}
	public void setAuthor(PlayerDTO author) {
		this.author = author;
	}
	public Instant getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}
	
}
