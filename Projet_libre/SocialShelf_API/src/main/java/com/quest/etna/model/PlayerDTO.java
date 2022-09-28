package com.quest.etna.model;

public class PlayerDTO {
    private String username;
    private UserRole role;
    private Integer playerId;



    public PlayerDTO(String username, UserRole role) {
        super();
        this.username = username;
        this.role = role;
    }

    public PlayerDTO(Player player) {
        super();
        this.username = player.getName();
        this.role = player.getRole();
        this.playerId = player.getId();
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public Integer getPlayerId() { return playerId;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
}
