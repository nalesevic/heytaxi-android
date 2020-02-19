package com.example.heytaxi_driver;

public class PassengerRequest {
    private String latitude;
    private String longitude;
    private String passengerLocationAddress;

    public PassengerRequest(String latitude, String longitude, String passengerLocationAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.passengerLocationAddress = passengerLocationAddress;
    }

    public String getPassengerLocationAddress() {
        return passengerLocationAddress;
    }

    public void setPassengerLocationAddress(String passengerLocationAddress) {
        this.passengerLocationAddress = passengerLocationAddress;
    }

    public PassengerRequest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
