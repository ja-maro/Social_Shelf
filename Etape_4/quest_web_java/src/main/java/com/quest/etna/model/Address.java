package com.quest.etna.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    
    @Column(name = "postal_code", columnDefinition = "varchar(30) not null unique")
    public String postalCode;
    
    @Column(name = "city", columnDefinition = "varchar(50) not null unique")
    public String city;
    
    @Column(name = "country", columnDefinition = "varchar(50) not null unique")
    public String country;
    
    @ManyToOne
    private User user;
    
    @CreationTimestamp
    @Column(name = "creation_date", columnDefinition = "datetime")
    private Instant creationDate;

    @UpdateTimestamp
    @Column(name = "updated_date", columnDefinition = "datetime")
    private Instant updatedDate;
    
    // CONSTRUCTORS

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(String street, String postalCode, String city, String country, User user) {
		super();
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.user = user;
	}

	// GETTERS & SETTERS

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	public Instant getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Instant updatedDate) {
		this.updatedDate = updatedDate;
	}
    
    
}