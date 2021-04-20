package project.java_63.condoinfo;

import project.java_63.models.Person;

import java.util.ArrayList;

public class Room {
    private String buildingName;
    private String floor;
    private String roomNumber;
    private String type;
    private int maxResident;
    private ArrayList<Person> persons;
    private Person currentPerson;

    public Room(String buildingName, String floor, String roomNumber, String type){
        persons = new ArrayList<>();
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.type = type;
        this.floor = floor;
        selectedMaxResident();
    }

    private void selectedMaxResident(){
        if(type.equals("Studio")){
            maxResident = 1;
        }else if(type.equals("One Bedroom")){
            maxResident = 2;
        }else if(type.equals("Two Bedrooms")){
            maxResident = 4;
        }
    }

    public boolean checkPerson(String name){
        for(Person p: persons){
            if(p.getName().equals(name)){
                currentPerson = p;
                return true;
            }
        }
        currentPerson = null;
        return false;
    }

    public boolean checkPersonInRoom(String name, String surname){
        for(Person p: persons){
            if(p.getName().equals(name) && p.getSurname().equals(surname)){
                currentPerson = p;
                return true;
            }
        }
        currentPerson = null;
        return false;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public int getMaxResident() {
        return maxResident;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public int countPersonInRoom() {
        int count = 0;
        for(Person p: persons){
            count += 1;
        }
        return count;
    }

    public void addPersonToRoom(Person person){
        persons.add(person);
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public String getFloor() {
        return floor;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }


    public void setMaxResident(int maxResident) {
        this.maxResident = maxResident;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }


    public String toStringPerson(){
        String line = "";
        for (Person person: persons){
            line += "Name : " + person.getName() + " " + person.getSurname() + "\n" + person.getPhoneNumber() + "\n";
        }
        return line;
    }

    public String toStringToWriteFile(){
        String line = "";
        int i = 1;
        for (Person person: persons){
            if(i == 1) {
                line += person.getName() + ", " + person.getSurname() + ", " + person.getPhoneNumber();
            }else {
                line += ", " + person.getName() + ", " + person.getSurname() + ", " + person.getPhoneNumber();
            }
            i += 1;
        }
        return line;
    }

    public String toStringResidentInRoom(){
        return countPersonInRoom() + "/" + maxResident;
    }

    public String toStringToTableColumn(){
        String line = "";
        for(Person person: persons){
            line += person.getName() + " " + person.getSurname();
            break;
        }
        return line;
    }
}
