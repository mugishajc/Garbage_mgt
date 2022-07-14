package com.example.garbagemanagement.Models;

public class Driver {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String workaddress;
    private String plateNumber;

    public Driver() {
    }

    public Driver(String email, String fullName, String phoneNumber, String workaddress, String plateNumber) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.workaddress = workaddress;
        this.plateNumber = plateNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkaddress() {
        return workaddress;
    }

    public void setWorkaddress(String workaddress) {
        this.workaddress = workaddress;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
