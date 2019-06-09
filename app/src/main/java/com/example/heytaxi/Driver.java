package com.example.heytaxi;

public class Driver {
    private int driverID;
    private String firstName;
    private String lastName;
    private String vehicle;
    private String company;
    private int rating;

    public Driver(String firstName, String lastName, String vehicle, String company, int rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
        this.company = company;
        this.rating = rating;
    }

    public Driver(int driverID, String firstName, String lastName, String vehicle, String company, int rating) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
        this.company = company;
        this.rating = rating;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDriverID() {
        return driverID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getCompany() {
        return company;
    }

    public int getRating() {
        return rating;
    }
}
