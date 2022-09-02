package com.quest.etna.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @Column(name = "street", columnDefinition = "varchar(100) not null unique")
    public String street;
    
    @Column(name = "postalCode", columnDefinition = "varchar(30) not null unique")
    public String postalCode;
    
    @Column(name = "city", columnDefinition = "varchar(50) not null unique")
    public String city;
    
    @Column(name = "country", columnDefinition = "varchar(50) not null unique")
    public String country;
    
    @ManyToOne
    private User user;
    
    @CreatedDate
    @Column(name = "creation_date", columnDefinition = "datetime")
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "updated_date", columnDefinition = "datetime")
    private Instant updatedDate;
}