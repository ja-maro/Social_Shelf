package com.quest.etna.model;

import com.quest.etna.model.player.Player;
import com.quest.etna.model.player.PlayerDTO;

public class AddressDTO {

    private Integer id;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private PlayerDTO user;

    public AddressDTO() {}

   public AddressDTO(int id, String street, String postalCode, String city, String country, PlayerDTO playerDTO) {
       this.id = id;
       this.street = street;
       this.city = city;
       this.country = country;
       this.postalCode = postalCode;
       this.user = playerDTO;
   }

    public AddressDTO(int id, String street, String postalCode, String city, String country, Player player) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.user = new PlayerDTO(player);
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.user = new PlayerDTO(address.getPlayer());
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

    public PlayerDTO getUser() {
        return user;
    }

    public void setUser(PlayerDTO playerDTO) {
        this.user = playerDTO;
    }

    public void setUserDTO(Player player) {
       this.user = new PlayerDTO(player);
    }
}
