package com.quest.etna.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "street", columnDefinition = "varchar(100) not null")
    private String street;
    
    @Column(name = "postal_code", columnDefinition = "varchar(30) not null")
    private String postalCode;
    
    @Column(name = "city", columnDefinition = "varchar(50) not null")
    private String city;
    
    @Column(name = "country", columnDefinition = "varchar(50) not null")
    private String country;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
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
	}

	public Address(AddressDTO addressDTO) {
		super();
		this.user = new User(addressDTO.getUser());
		this.city = addressDTO.getCity();
		this.id = addressDTO.getId();
		this.country = addressDTO.getCountry();
		this.postalCode = addressDTO.getPostalCode();
		this.street = addressDTO.getStreet();
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
		user.setPassword("*****");
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