package com.quest.etna.model;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @Column(name = "username", columnDefinition = "varchar(255) not null unique")
    public String username;

    @Column(name = "password", columnDefinition = "varchar(255) not null")
    private String password;

    @Column(name = "role", columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    @CreatedDate
    @Column(name = "creation_date", columnDefinition = "datetime")
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "updated_date", columnDefinition = "datetime")
    private Date updatedDate;

    public User() {
    }

    public User(String username, UserRole role, Date creationDate, Date updatedDate) {
        this.username = username;
        this.role = role;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return role;
    }

    public void setUserRole(UserRole role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(role, user.role)
                && Objects.equals(creationDate, user.creationDate)
                && Objects.equals(updatedDate, user.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role, password, creationDate, updatedDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "username:'" + username + '\'' +
                ", role:'" + role + '\'' +
                ", creation_date:" + creationDate +
                ", updated_date:" + updatedDate +
                "}";
    }
}
