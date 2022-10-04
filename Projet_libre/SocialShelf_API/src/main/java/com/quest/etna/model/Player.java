package com.quest.etna.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.quest.etna.model.DTO.PlayerDTO;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username", columnDefinition = "varchar(255) not null")
	private String username;

	@Column(name = "email", columnDefinition = "varchar(255) not null unique")
	private String email;

	@Column(name = "password", columnDefinition = "varchar(255) not null")
	private String password;

	@Column(name = "role", columnDefinition = "varchar(10)")
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.ROLE_USER;

	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Address> addresses;

	@ManyToMany
	(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) 
	@JoinTable(name = "players_games", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "game_id"))
	private Set<Game> games = new HashSet<>();

	@OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Event> organized_events;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "participants", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	private Set<Event> participated_events;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Message> messages;

	@Column(name = "disable_date", columnDefinition = "datetime")
	private Instant disableDate;

	@CreationTimestamp
	@Column(name = "creation_date", columnDefinition = "datetime")
	private Instant creationDate;

	@UpdateTimestamp
	@Column(name = "updated_date", columnDefinition = "datetime")
	private Instant updatedDate;

// CONSTRUCTORS

	public Player(PlayerDTO player) {
		super();
		this.username = player.getUsername();
		this.email = player.getEmail();
		this.role = player.getRole();
		this.id = player.getPlayerId();
	}

	public Player() {}

// GETTERS & SETTERS

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public Set<Event> getOrganized_events() {
		return organized_events;
	}

	public void setOrganized_events(Set<Event> organized_events) {
		this.organized_events = organized_events;
	}

	public Set<Event> getParticipated_events() {
		return participated_events;
	}

	public void setParticipated_events(Set<Event> participated_events) {
		this.participated_events = participated_events;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Instant getDisableDate() {
		return disableDate;
	}

	public void setDisableDate(Instant disableDate) {
		this.disableDate = disableDate;
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
	
	public void addGame(Game game) {
		this.games.add(game);
	}

	public void removeGame(Game game) {	
		games.remove(game);
		game.getPlayers().remove(this);
	}

}
