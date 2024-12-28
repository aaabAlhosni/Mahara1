package com.example.mahara1.restaurantOwner.data;

public class Restaurant {
    private String ownerName;
    private String corporationNum;
    private String additionalDetails;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantDescription;
    private String restaurantPhone;
    private String restaurantEmail;
    private String restaurantPassword;
    private String logoUrl;
    private String cuisineCategory;
    // Add more fields as needed

    public Restaurant(String restaurantEmail, String corporationNumber, String additionalDetails,
                      String restaurantName, String restaurantAddress, String restaurantDescription,
                      String restaurantPhone, String logoUrl,String cuisineCategory) {
        this.restaurantEmail = restaurantEmail;
        this.corporationNum = corporationNumber;
        this.additionalDetails = additionalDetails;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantDescription = restaurantDescription;
        this.restaurantPhone = restaurantPhone;
        this.logoUrl = logoUrl;
        this.cuisineCategory = cuisineCategory;
    }

    // Add getters and setters for all fields
    public String getOwnerName() {
        return ownerName;
    }

    public String getCuisineCategory() {
        return cuisineCategory;
    }

    public void setCuisineCategory(String cuisineCategory) {
        this.cuisineCategory = cuisineCategory;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCorporationNum() {
        return corporationNum;
    }

    public void setCorporationNum(String corporationNum) {
        this.corporationNum = corporationNum;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public void setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
    }

    public String getRestaurantPassword() {
        return restaurantPassword;
    }

    public void setRestaurantPassword(String restaurantPassword) {
        this.restaurantPassword = restaurantPassword;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
