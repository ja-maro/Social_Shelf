package com.quest.etna.model;

public class UserDetails {
    private final String username;
    private final UserRole role;
    
    

    public UserDetails(String username, UserRole role) {
		super();
		this.username = username;
		this.role = role;
	}
    
    public UserDetails(Player player) {
    	super();
    	this.username = player.getName();
    	this.role = player.getRole();
    }

	public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
