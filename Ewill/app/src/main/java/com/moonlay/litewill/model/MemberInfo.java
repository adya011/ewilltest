package com.moonlay.litewill.model;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 7/11/2018.
 */

public class MemberInfo {
    private int id;
    private String userName;
    private String email;
    private String fullName;
    private String mobileNumber;
    private String dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String passportNo;
    private int memberTypeId;
    private String countryCode;
    private String userIdentityId;
    private int actionCounter;
    private int defaultActionCounter;
    private ArrayList<Subscription> subscriptions;

    public MemberInfo(int id, String userName, String email, String fullName, String mobileNumber, String dateOfBirth, String placeOfBirth, String gender, String passportNo, int memberTypeId, String countryCode, String userIdentityId, int actionCounter, int defaultActionCounter, ArrayList<Subscription> subscriptions) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.passportNo = passportNo;
        this.memberTypeId = memberTypeId;
        this.countryCode = countryCode;
        this.userIdentityId = userIdentityId;
        this.actionCounter = actionCounter;
        this.defaultActionCounter = defaultActionCounter;
        this.subscriptions = subscriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public int getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(int memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserIdentityId() {
        return userIdentityId;
    }

    public void setUserIdentityId(String userIdentityId) {
        this.userIdentityId = userIdentityId;
    }

    public int getActionCounter() {
        return actionCounter;
    }

    public void setActionCounter(int actionCounter) {
        this.actionCounter = actionCounter;
    }

    public int getDefaultActionCounter() {
        return defaultActionCounter;
    }

    public void setDefaultActionCounter(int defaultActionCounter) {
        this.defaultActionCounter = defaultActionCounter;
    }

    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
