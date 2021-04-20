package project.java_63.condoinfo;

import java.util.ArrayList;

public class Condo {
    private ArrayList<Room> rooms;
    private Room currentRoom;
    private Room currentRoomUser;

    public Condo(){
        rooms = new ArrayList<>();
    }

    public boolean checkRoom(String roomNumber){
        for(Room r: rooms){
            if(r.getRoomNumber().equals(roomNumber)){
                currentRoom = r;
                return true;
            }
        }
        currentRoom = null;
        return false;
    }

    public boolean checkRoomByName(String name, String surname){
        for(Room room: rooms){
            if(room.checkPersonInRoom(name, surname)){
                currentRoomUser = room;
                return true;
            }
        }
        currentRoomUser = null;
        return false;
    }

    public Room getCurrentRoomUser() {
        return currentRoomUser;
    }

    public int countRoomInFloor(String building, String floor){
        int count = 1;
//        System.out.println(rooms);
        for(Room r: rooms){
            if(r.getFloor().equals(floor) && r.getBuildingName().equals(building)){
                count += 1;
            }
        }
        return count;
    }

//    public ArrayList<String> nameCond


    public void addRoomToCondo(Room room){
        rooms.add(room);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public String toString_NextRoom(String building, String floor){
        String nextRoom = floor;
//        System.out.println(building + " " + floor);
        if(countRoomInFloor(building, floor) < 10) {
            nextRoom += "0" + countRoomInFloor(building, floor);
        }else{
            nextRoom += countRoomInFloor(building, floor);
        }
        return nextRoom;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }
}
