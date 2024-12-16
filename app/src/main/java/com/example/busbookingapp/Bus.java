package com.example.busbookingapp;

public class Bus {
    private final String busName;
    private final String busType;
    private final String departureTime;
    private final String arrivalTime;
    private final String source;
    private final String destination;
    private String busId; // Bus ID

    // Constructor for Bus with bus ID
    public Bus(String busId, String busName, String busType, String departureTime, String arrivalTime, String source, String destination) {
        this.busId = busId;
        this.busName = busName;
        this.busType = busType;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.source = source;
        this.destination = destination;
    }

    // Getters
    public String getBusName() {
        return busName;
    }

    public String getBusType() {
        return busType;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getBusId() {
        return busId; // Get bus ID
    }

    // Setters (if needed)
    public void setBusId(String busId) {
        this.busId = busId; // Set bus ID
    }
}
