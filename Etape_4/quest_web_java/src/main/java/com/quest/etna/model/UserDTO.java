package com.quest.etna.model;

public class UserDTO {
    private String username;
    private UserRole role;
    private Integer user_id;



    public UserDTO(String username, UserRole role) {
        super();
        this.username = username;
        this.role = role;
    }

    public UserDTO(User user) {
        super();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.user_id = user.getId();
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public Integer getUser_id() { return user_id;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
