package com.example.mahara1.restaurantOwner.data;

public class Booking {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String restaurantId;
    private String ownerPhone;
    private String ownerEmail;
    // Add more fields as needed

    public Booking(String customerName, String customerEmail, String customerPhone,
                   String restaurantId, String ownerEmail, String ownerPhone) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.restaurantId = restaurantId;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
    }

    // Add getters and setters for all fields

    public String getcustomerEmail() {
        return customerEmail;
    }

    public void setcustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getcustomerPhone() {
        return customerPhone;
    }

    public void setcustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getrestaurantId() {
        return restaurantId;
    }

    public void setrestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getownerPhone() {
        return ownerPhone;
    }

    public void setownerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getownerEmail() {
        return ownerEmail;
    }

    public void setownerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getcustomerName() {
        return customerName;
    }

    public void setcustomerName(String customerName) {
        this.customerName = customerName;
    }

}
