package com.quest.etna.model;

public class UserDetails {
    private String username;
    private UserRole role;
    
    

    public UserDetails(String username, UserRole role) {
		super();
		this.username = username;
		this.role = role;
	}
    
    public UserDetails(User user) {
    	super();
    	this.username = user.getUsername();
    	this.role = user.getUserRole();
    }

	public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
