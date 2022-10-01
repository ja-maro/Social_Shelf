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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.quest.etna.model.DTO.GameDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", columnDefinition = "varchar(255) not null unique")
	private String name;

	@Column(name = "publisher", columnDefinition = "varchar(255) not null")
	private String publisher;

	@Column(name = "description", columnDefinition = "varchar(4000) not null")
	private String description;

	@Column(name = "min_player", columnDefinition = "TINYINT not null")
	private int minPlayer;

	@Column(name = "max_player", columnDefinition = "TINYINT not null")
	private int maxPlayer;

	@Column(name = "average_duration", columnDefinition = "SMALLINT not null")
	private int averageDuration;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "games_types", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
	private Set<GameType> types;
	
	@ManyToMany(mappedBy = "games", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Player> owners;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Event> events;

	@CreationTimestamp
	@Column(name = "creation_date", columnDefinition = "datetime")
	private Instant creationDate;

	@UpdateTimestamp
	@Column(name = "updated_date", columnDefinition = "datetime")
	private Instant updatedDate;

// CONSTRUCTORS

	public Game() {
		super();
	}

	public Game(GameDTO gameDTO) {
		this.name = gameDTO.getName();
		this.publisher = gameDTO.getPublisher();
		this.description = gameDTO.getDescription();
		this.minPlayer = gameDTO.getMinPlayer();
		this.maxPlayer = gameDTO.getMaxPlayer();
		this.averageDuration = gameDTO.getAverageDuration();
		this.types = gameDTO.getGameType();
	}


// GETTERS & SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getAverageDuration() {
		return averageDuration;
	}

	public void setAverageDuration(int averageDuration) {
		this.averageDuration = averageDuration;
	}

	public Set<Player> getOwners() {
		return owners;
	}

	public void setOwners(Set<Player> owners) {
		this.owners = owners;
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

	public Set<GameType> getTypes() {
		return types;
	}

	public void setTypes(Set<GameType> types) {
		this.types = types;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
}
