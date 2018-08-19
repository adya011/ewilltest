package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/5/2018.
 */

public class UpdateWillAddress {
    private int addressId;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private double longitude;
    private double latitude;

    public UpdateWillAddress(int addressId, String street, String city, String country, String zipCode, double longitude, double latitude) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
