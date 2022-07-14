package com.example.garbagemanagement.Models;

public class Account {
    private String email;
    private String fullName;
    private String city;
    private String sector;
    private String streetno;
    private String phoneNumber;

    public Account() {
    }

    public Account(String email, String fullName, String city, String sector, String streetno, String phoneNumber) {
        this.email = email;
        this.fullName = fullName;
        this.city = city;
        this.sector = sector;
        this.streetno = streetno;
        this.phoneNumber = phoneNumber;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getStreetno() {
        return streetno;
    }

    public void setStreetno(String streetno) {
        this.streetno = streetno;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
