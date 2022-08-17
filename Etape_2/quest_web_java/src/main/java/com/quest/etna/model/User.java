package com.quest.etna.model;

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
    private UserRole userRole = UserRole.ROLE_USER;

    @CreatedDate
    @Column(name = "creation_date", columnDefinition = "datetime")
    private String creationDate;

    @LastModifiedDate
    @Column(name = "updated_date", columnDefinition = "datetime")
    private String updatedDate;
}
