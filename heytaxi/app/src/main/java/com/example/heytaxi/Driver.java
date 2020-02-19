package com.example.heytaxi;

public class Driver {
    private int driverID;
    private String firstName;
    private String lastName;
    private String vehicleType;
    private String company;
    private String driverLocationLatitude;
    private String driverLocationLongitude;
    private String rating;

    public Driver(int driverID, String firstName, String lastName, String vehicleType, String company, String driverLocationLatitude, String driverLocationLongitude, String rating) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleType = vehicleType;
        this.company = company;
        this.driverLocationLatitude = driverLocationLatitude;
        this.driverLocationLongitude = driverLocationLongitude;
        this.rating = rating;
    }

    public String getDriverLocationLatitude() {
        return driverLocationLatitude;
    }

    public void setDriverLocationLatitude(String driverLocationLatitude) {
        this.driverLocationLatitude = driverLocationLatitude;
    }

    public String getDriverLocationLongitude() {
        return driverLocationLongitude;
    }

    public void setDriverLocationLongitude(String driverLocationLongitude) {
        this.driverLocationLongitude = driverLocationLongitude;
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
