package com.myapp.ws.ws_app.entities;

import com.myapp.ws.ws_app.shared.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = -7858125838612315511L;
    @Id
    @GeneratedValue
    private long id;
    @Column(length=50, nullable=false)
    private String addressId;
    @Column(length=20, nullable=false)
    private String country;
    @Column(length=20, nullable=false)
    private String city;
    @Column(length=50, nullable=false)
    private String street;
    @Column(length=7, nullable=false)
    private String postal;
    @Column(length=20, nullable=false)
    private String type;
    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
