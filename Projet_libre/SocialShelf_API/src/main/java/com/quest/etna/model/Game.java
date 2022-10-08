package com.quest.etna.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quest.etna.model.DTO.GameDTO;

import org.hibernate.annotations.Cascade;
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

	@ManyToMany
	(cascade = CascadeType.PERSIST)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) 
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "games_types", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
	private Set<GameType> types = new HashSet<>();
	
	@ManyToMany(mappedBy = "games")
	@JsonIgnore
	private Set<Player> players = new HashSet<>();

	@OneToMany(mappedBy = "game", cascade =  { CascadeType.ALL })
	private Set<Event> events;

	@JsonIgnore
	@CreationTimestamp
	@Column(name = "creation_date", columnDefinition = "datetime")
	private Instant creationDate;

	@JsonIgnore
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

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
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

	public void addType(GameType gameType) {
		types.add(gameType);
	}

	public void removeType(GameType gameType) {
		types.remove(gameType);
		gameType.getGames().remove(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(averageDuration, creationDate, description, id, maxPlayer, minPlayer, name, publisher,
				updatedDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return averageDuration == other.averageDuration && Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(description, other.description) && id == other.id && maxPlayer == other.maxPlayer
				&& minPlayer == other.minPlayer && Objects.equals(name, other.name)
				&& Objects.equals(publisher, other.publisher) && Objects.equals(updatedDate, other.updatedDate);
	}	

	
}
