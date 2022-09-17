package com.quest.etna.model;

public class AddressDTO {

    private Integer id;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private UserDTO user;

   public AddressDTO(int id, String street, String postalCode, String city, String country, UserDTO userDTO) {
       this.id = id;
       this.street = street;
       this.city = city;
       this.country = country;
       this.postalCode = postalCode;
       this.user = userDTO;
   }

    public AddressDTO(int id, String street, String postalCode, String city, String country, User user) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.user = new UserDTO(user);
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.user = new UserDTO(address.getUser());
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO userDTO) {
        this.user = userDTO;
    }

    public void setUserDTO(User user) {
       this.user = new UserDTO(user);
    }
}
