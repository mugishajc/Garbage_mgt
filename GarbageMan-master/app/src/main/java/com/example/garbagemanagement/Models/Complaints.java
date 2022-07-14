package com.example.garbagemanagement.Models;


import java.util.Map;

public class Complaints {
    String email,phone,city,sector,streetno,driveremail,message,status,givendate,replialdate,id;
//    public Map time;

//    Map time,

    public Complaints() {
    }

    public Complaints(String email, String phone, String city, String sector, String streetno, String driveremail, String message, String status,String givendate,String replialdate, String id) {
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.sector = sector;
        this.streetno = streetno;
        this.driveremail = driveremail;
        this.message = message;
        this.status = status;
       this.givendate=givendate;
       this.replialdate=replialdate;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDriveremail() {
        return driveremail;
    }

    public void setDriveremail(String driveremail) {
        this.driveremail = driveremail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getGivendate() {
        return givendate;
    }

    public void setGivendate(String givendate) {
        this.givendate = givendate;
    }

    public String getReplialdate() {
        return replialdate;
    }

    public void setReplialdate(String replialdate) {
        this.replialdate = replialdate;
    }

    public void setId(String id) {
        this.id = id;
    }
}
