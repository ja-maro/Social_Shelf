package com.quest.etna.model.DTO;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.quest.etna.model.Address;
import com.quest.etna.model.Event;
import com.quest.etna.model.Game;
import com.quest.etna.model.Player;

public class EventDTO {

	private int id;
	private String title;
	private String pitch;
	private int minPlayer;
	private int maxPlayer;
	private int duration;
	private Instant startDate;
	private Instant cancelDate;
	private AddressDTO place;
	private GameDTO game;
	private PlayerDTO organizer;
	
	
	
	

	public EventDTO() {
		super();
	}
	
	public EventDTO(int id, String title, String pitch, int minPlayer, int maxPlayer, int duration, Instant startDate,
			Instant cancelDate, AddressDTO place, GameDTO game, PlayerDTO organizer) {
		super();
		this.id = id;
		this.title = title;
		this.pitch = pitch;
		this.minPlayer = minPlayer;
		this.maxPlayer = maxPlayer;
		this.duration = duration;
		this.startDate = startDate;
		this.cancelDate = cancelDate;
		this.place = place;
		this.game = game;
		this.organizer = organizer;
	}
	public EventDTO(Event event) {
		super();
		this.id = event.getId();
		this.title = event.getTitle();
		this.pitch = event.getPitch();
		this.minPlayer = event.getMinPlayer();
		this.maxPlayer = event.getMaxPlayer();
		this.duration = event.getDuration();
		this.startDate = event.getStartDate();
		this.cancelDate = event.getCancelDate();
		
		this.place = new AddressDTO(event.getPlace());
		this.game = new GameDTO(event.getGame());
		this.organizer = new PlayerDTO(event.getOrganizer());
	}
	
	
	
	
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
	public void setCancelDate(Instant cancellationDate) {
		this.cancelDate = cancellationDate;
	}
	public AddressDTO getPlace() {
		return place;
	}
	public void setPlace(AddressDTO place) {
		this.place = place;
	}
	public GameDTO getGame() {
		return game;
	}
	public void setGame(GameDTO game) {
		this.game = game;
	}
	public PlayerDTO getOrganizer() {
		return organizer;
	}
	public void setOrganizer(PlayerDTO organizer) {
		this.organizer = organizer;
	}


}
