package com.quest.etna.model.DTO;

import com.quest.etna.model.Game;
import com.quest.etna.model.GameType;

import java.util.Set;

public class GameDTO {
	
    private Integer id;
    private String name;
    private String publisher;
    private String description;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer averageDuration;
    private Set<GameType> gameType;


    public GameDTO(Integer id,
                   String name,
                   String publisher,
                   String description,
                   Integer minPlayer,
                   Integer maxPlayer,
                   Integer averageDuration,
                   Set<GameType> gameType) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.description = description;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.averageDuration = averageDuration;
        this.gameType = gameType;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.publisher = game.getPublisher();
        this.description = game.getDescription();
        this.minPlayer = game.getMinPlayer();
        this.maxPlayer = game.getMaxPlayer();
        this.averageDuration = game.getAverageDuration();
        this.gameType = game.getTypes();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public Integer getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(Integer averageDuration) {
        this.averageDuration = averageDuration;
    }

    public Set<GameType> getGameType() {
        return gameType;
    }

    public void setGameType(Set<GameType> gameType) {
        this.gameType = gameType;
    }
}
