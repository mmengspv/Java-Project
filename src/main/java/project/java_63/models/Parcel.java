package project.java_63.models;

import project.java_63.models.Letter;

public class Parcel extends Letter {
    private String delivery;
    private String trackingNumber;

    public Parcel(String type, String nameReceiver, String surnameReceiver, String roomNumber, String nameSender, String surnameSender, String width, String height, String delivery, String trackingNumber) {
        super(type, nameReceiver, surnameReceiver, roomNumber, nameSender, surnameSender, width, height);
        this.delivery = delivery;
        this.trackingNumber = trackingNumber;
    }

    public String getDelivery() {
        return delivery;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }
}
