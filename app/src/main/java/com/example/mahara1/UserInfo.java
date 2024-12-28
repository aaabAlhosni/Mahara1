package com.example.mahara1;

public class UserInfo {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String userType;
    private String password;

    public String getCorporationNumber() {
        return CorporationNumber;
    }

    public void setCorporationNumber(String corporationNumber) {
        this.CorporationNumber = corporationNumber;
    }

    private String CorporationNumber;
    public UserInfo(String firstName, String lastName, String email, String phone, String userType, String password,String corporationNumber) {
        this.name = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.password = password;
        this.CorporationNumber =corporationNumber;
    }

    // Getter methods for user information
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }
}
