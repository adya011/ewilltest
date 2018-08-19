package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 3/16/2018.
 */

public class RegisterUser {
    private String pin;
    private String userName;
    private String email;
    private String password;
    private String mobileNumber;
    private String securityQuestion;
    private String securityAnswer;
    private String countryCode;
    private boolean isUserTest;

    public RegisterUser(String pin, String userName, String email, String password, String mobileNumber, String securityQuestion, String securityAnswer, String countryCode, boolean isUserTest) {
        this.pin = pin;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.countryCode = countryCode;
        this.isUserTest = isUserTest;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isUserTest() {return isUserTest;}

    public void setUserTest(boolean userTest) {isUserTest = userTest;}
}
