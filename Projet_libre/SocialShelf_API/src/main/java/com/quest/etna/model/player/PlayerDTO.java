package com.quest.etna.model.player;

import com.quest.etna.model.UserRole;

public class PlayerDTO {
    private String username;
    private UserRole role;
    private Integer playerId;
    private String email;



    public PlayerDTO(String username, UserRole role, String email) {
        super();
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public PlayerDTO(Player player) {
        super();
        this.username = player.getUsername();
        this.role = player.getRole();
        this.playerId = player.getId();
        this.email = player.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
