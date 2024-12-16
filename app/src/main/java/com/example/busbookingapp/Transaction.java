package com.example.busbookingapp;

import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String busId;
    private String seats;
    private String idNumber;
    private String paymentMethod;
    private String paymentId;
    private long date;
    private boolean cancelled;

    public Transaction(long id, String busId, String seats, String idNumber, String paymentMethod, String paymentId, long date) {
        this.id = id;
        this.busId = busId;
        this.seats = seats;
        this.idNumber = idNumber;
        this.paymentMethod = paymentMethod;
        this.paymentId = paymentId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getBusId() {
        return busId;
    }

    public String getSeats() {
        return seats;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public long getDate() {
        return date;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
