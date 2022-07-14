package com.example.garbagemanagement.Models;

public class Bin {
    private String city;
    private String sector;
    private String streetno;
    private String useremail,id;

    public Bin() {
    }

    public Bin(String city, String sector, String streetno, String useremail,String id) {
        this.city = city;
        this.sector = sector;
        this.streetno = streetno;
        this.useremail = useremail;
        this.id=id;
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

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
