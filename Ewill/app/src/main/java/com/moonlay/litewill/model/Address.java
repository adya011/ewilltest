package com.moonlay.litewill.model;

import java.io.Serializable;

/**
 * Created by nandana.samudera on 6/26/2018.
 */

public class Address implements Serializable{
    private int id;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private double longitude;
    private double latitude;

    public Address(String street, String city, String country, String zipCode, double longitude, double latitude) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
