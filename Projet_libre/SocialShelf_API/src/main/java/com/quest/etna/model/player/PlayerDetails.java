package com.quest.etna.model.player;

import com.quest.etna.model.UserRole;

public class PlayerDetails {
    private final String username;
    private final UserRole role;
    private final String email;
    
    

    public PlayerDetails(String username, UserRole role, String email) {
		super();
		this.username = username;
		this.role = role;
        this.email = email;
	}
    
    public PlayerDetails(Player player) {
    	super();
    	this.username = player.getUsername();
    	this.role = player.getRole();
        this.email = player.getEmail();
    }

	public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}
