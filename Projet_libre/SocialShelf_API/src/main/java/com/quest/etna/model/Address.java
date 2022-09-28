package com.quest.etna.model;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.quest.etna.model.player.Player;
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
    private Player player;
    
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Event> events;
    
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

//	public Address(AddressDTO addressDTO) {
//		super();
//		this.player = new Player(addressDTO.getPlayer());
//		this.city = addressDTO.getCity();
//		this.id = addressDTO.getId();
//		this.country = addressDTO.getCountry();
//		this.postalCode = addressDTO.getPostalCode();
//		this.street = addressDTO.getStreet();
//	}

	public Address(String street, String postalCode, String city, String country, Player player) {
		super();
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.player = player;
	}

	public Address(AddressDTO addressDTO) {
		this.street = addressDTO.getStreet();
		this.id = addressDTO.getId();
		this.city = addressDTO.getCity();
		this.country = addressDTO.getCountry();
		this.postalCode = addressDTO.getPostalCode();
	}

	// GETTERS & SETTERS

	public Integer getId() {
		return id;
	}
	
	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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