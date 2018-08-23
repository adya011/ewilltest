package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 6/25/2018.
 */

public class UpgradeMaker {
    private String passportNo;
    private String fullName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationality;
    private String gender;
    private String address;
    private boolean isSingaporean;
    private String productCode;

    public UpgradeMaker(String passportNo, String fullName, String dateOfBirth, String placeOfBirth, String nationality, String gender, String address, boolean isSingaporean, String productCode) {
        this.passportNo = passportNo;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.nationality = nationality;
        this.gender = gender;
        this.address = address;
        this.isSingaporean = isSingaporean;
        this.productCode = productCode;

    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSingaporean() {
        return isSingaporean;
    }

    public void setSingaporean(boolean singaporean) {
        isSingaporean = singaporean;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
