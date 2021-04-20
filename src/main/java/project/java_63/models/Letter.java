package project.java_63.models;

public class Letter {
    private String dateIn = "-";
    private String dateOut = "-";
    private String pickupBy = "-";
    private String staffReceiver = "-";
    private String type;
    private String nameReceiver;
    private String surnameReceiver;
    private String roomNumber;
    private String nameSender;
    private String surnameSender;
    private String width;
    private String height;
    private String image = "data/images/default_parcel_icon.png";
    private String status = "Not received";
    private String staffOut = "-";

    public Letter(String type, String nameReceiver, String surnameReceiver, String roomNumber, String nameSender, String surnameSender, String width, String height) {
        this.type = type;
        this.nameReceiver = nameReceiver;
        this.surnameReceiver = surnameReceiver;
        this.roomNumber = roomNumber;
        this.nameSender = nameSender;
        this.surnameSender = surnameSender;
        this.width = width;
        this.height = height;
    }

    public String getStaffOut() {
        return staffOut;
    }

    public String getDateIn() { return dateIn; }
    public String getRoomNumber() { return roomNumber; }
    public String getNameSender() {
        return nameSender;
    }
    public String getSurnameSender() {
        return surnameSender;
    }
    public String getWidth() {
        return width;
    }
    public String getHeight() {
        return height;
    }
    public String getType() { return type; }
    public String getNameReceiver() { return nameReceiver; }
    public String getSurnameReceiver() { return surnameReceiver; }
    public String getImage() { return image; }
    public String getStatus() { return status; }
    public String getDateOut() { return dateOut; }
    public String getPickupBy() { return pickupBy; }
    public String getStaffReceiver() { return staffReceiver; }

    public String toStringReceiver(){
        return  nameReceiver + " " + surnameReceiver;
    }
    public String toStringSender(){
        return nameSender + " " + surnameSender;
    }

    public void setStaffOut(String staffOut) {
        this.staffOut = staffOut;
    }

    public void setType(String type) { this.type = type; }
    public void setNameReceiver(String nameReceiver) { this.nameReceiver = nameReceiver; }
    public void setSurnameReceiver(String surnameReceiver) { this.surnameReceiver = surnameReceiver; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setNameSender(String nameSender) { this.nameSender = nameSender; }
    public void setSurnameSender(String surnameSender) { this.surnameSender = surnameSender; }
    public void setWidth(String width) { this.width = width; }
    public void setHeight(String height) { this.height = height; }
    public void setImage(String image) { this.image = image; }
    public void setDateIn(String dateIn) { this.dateIn = dateIn; }
    public void setStatus(String status) { this.status = status; }
    public void setDateOut(String dateOut) { this.dateOut = dateOut; }
    public void setPickupBy(String pickupBy) { this.pickupBy = pickupBy; }
    public void setStaffReceiver(String staffReceiver) { this.staffReceiver = staffReceiver; }
}
