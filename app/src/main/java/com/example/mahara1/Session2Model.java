package com.example.mahara1;

public class Session2Model {
    String name,Address;

    public Session2Model(String name, String address) {
        this.name = name;
        Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
