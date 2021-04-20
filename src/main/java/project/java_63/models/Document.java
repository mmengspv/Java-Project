package project.java_63.models;

import project.java_63.models.Letter;

public class Document extends Letter {
    private String importance;


    public Document(String type, String nameReceiver, String surnameReceiver, String roomNumber, String nameSender, String surnameSender, String width, String height, String importance) {
        super(type, nameReceiver, surnameReceiver, roomNumber, nameSender, surnameSender, width, height);
        this.importance = importance;
    }

    public String getImportance(){
        return importance;
    }
}
