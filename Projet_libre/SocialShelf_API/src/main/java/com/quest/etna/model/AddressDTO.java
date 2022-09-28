package com.quest.etna.model;

import com.quest.etna.model.player.Player;
import com.quest.etna.model.player.PlayerDTO;

public class AddressDTO {

    private Integer id;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private PlayerDTO player;

    public AddressDTO() {}

   public AddressDTO(int id, String street, String postalCode, String city, String country, PlayerDTO playerDTO) {
       this.id = id;
       this.street = street;
       this.city = city;
       this.country = country;
       this.postalCode = postalCode;
       this.player = playerDTO;
   }

    public AddressDTO(int id, String street, String postalCode, String city, String country, Player player) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.player = new PlayerDTO(player);
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.player = new PlayerDTO(address.getPlayer());
    }

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

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO playerDTO) {
        this.player = playerDTO;
    }

    public void setUserDTO(Player player) {
       this.player = new PlayerDTO(player);
    }
}
