package com.quest.etna.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", columnDefinition = "varchar(4000) not null")
	private String content;

	@Column(name = "delete_date", columnDefinition = "datetime")
	private Instant deleteDate;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Event event;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Player author;

	@CreationTimestamp
	@Column(name = "creation_date", columnDefinition = "datetime")
	private Instant creationDate;

	@UpdateTimestamp
	@Column(name = "updated_date", columnDefinition = "datetime")
	private Instant updatedDate;

// CONSTRUCTORS
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

// GETTERS & SETTERS
	
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

	public Instant getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Instant deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Player getAuthor() {
		return author;
	}

	public void setAuthor(Player author) {
		this.author = author;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	public Instant getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Instant updatedDate) {
		this.updatedDate = updatedDate;
	}
}
