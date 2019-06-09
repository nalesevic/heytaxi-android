package com.example.heytaxi;

public class Driver {
    private int driverID;
    private String firstName;
    private String lastName;
    private String vehicleType;
    private String company;
    private String driverLocation;
    private String rating;

    public Driver(String firstName, String lastName, String vehicleType, String company, String driverLocation, String rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleType = vehicleType;
        this.company = company;
        this.driverLocation = driverLocation;
        this.rating = rating;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getDriverLocation() {
        return driverLocation;
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

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRating(String rating) {
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

    public String getVehicleType() {
        return vehicleType;
    }

    public String getCompany() {
        return company;
    }

    public String getRating() {
        return rating;
    }
}
