package com.quest.etna.model;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.quest.etna.model.DTO.EventDTO;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title", columnDefinition = "varchar(100) not null")
	private String title;

	@Column(name = "pitch", columnDefinition = "varchar(255) not null")
	private String pitch;

	@Column(name = "min_participants", columnDefinition = "TINYINT not null")
	private int minPlayer;

	@Column(name = "max_participants", columnDefinition = "TINYINT not null")
	private int maxPlayer;

	@Column(name = "duration", columnDefinition = "SMALLINT not null")
	private int duration;

	@Column(name = "start_date", columnDefinition = "datetime not null")
	private Instant startDate;

	@Column(name = "cancel_date", columnDefinition = "datetime")
	private Instant cancelDate;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Address place;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Game game;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Player organizer;

	@ManyToMany(mappedBy = "participated_events", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Player> participants;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Message> messages;

	@CreationTimestamp
	@Column(name = "creation_date", columnDefinition = "datetime")
	private Instant creationDate;

	@UpdateTimestamp
	@Column(name = "updated_date", columnDefinition = "datetime")
	private Instant updatedDate;


// CONSTRUCTORS
	public Event() {
		super();
	}

	public Event(EventDTO eventDTO) {
		this.id = eventDTO.getId();
		this.title = eventDTO.getTitle();
		this.pitch = eventDTO.getPitch();
		this.minPlayer = eventDTO.getMinPlayer();
		this.maxPlayer = eventDTO.getMaxPlayer();
		this.duration = eventDTO.getDuration();
		this.startDate = eventDTO.getStartDate();
		this.cancelDate = eventDTO.getCancelDate();
		this.place = new Address(eventDTO.getPlace());
		this.game = new Game(eventDTO.getGame());
		this.organizer = new Player(eventDTO.getOrganizer());
	}

// GETTERS & SETTERS
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPitch() {
		return pitch;
	}


	public void setPitch(String pitch) {
		this.pitch = pitch;
	}


	public int getMinPlayer() {
		return minPlayer;
	}


	public void setMinPlayer(int minPlayer) {
		this.minPlayer = minPlayer;
	}


	public int getMaxPlayer() {
		return maxPlayer;
	}


	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public Instant getStartDate() {
		return startDate;
	}


	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}


	public Instant getCancelDate() {
		return cancelDate;
	}


	public void setCancelDate(Instant cancelDate) {
		this.cancelDate = cancelDate;
	}


	public Address getPlace() {
		return place;
	}


	public void setPlace(Address place) {
		this.place = place;
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	public Player getOrganizer() {
		return organizer;
	}


	public void setOrganizer(Player organizer) {
		this.organizer = organizer;
	}


	public Set<Player> getParticipants() {
		return participants;
	}


	public void setParticipants(Set<Player> participants) {
		this.participants = participants;
	}


	public Set<Message> getMessages() {
		return messages;
	}


	public void setMessages(Set<Message> messages) {
		this.messages = messages;
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
