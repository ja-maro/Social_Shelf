package com.quest.etna.model;

public class UserDetails {
    private String username;
    private UserRole role;
    
    

    public UserDetails(String username, UserRole role) {
		super();
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
