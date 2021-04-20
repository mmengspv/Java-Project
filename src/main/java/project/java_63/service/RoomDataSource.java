package project.java_63.service;

import project.java_63.models.Person;
import project.java_63.condoinfo.Condo;
import project.java_63.condoinfo.Room;

import java.io.*;

public class RoomDataSource {
    private Condo condoList;
    private String fileDirectoryName;
    private String fileName;

    public RoomDataSource(String fileDirector, String fileName){
        this.fileDirectoryName = fileDirector;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    private void readRoomData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null){
            String[] data = line.split(", ");
            String buildingName = data[0].trim();
            String floor = data[1].trim();
            String roomNumber = data[2].trim();
            String type = data[3].trim();

            Room room = new Room(buildingName, floor, roomNumber, type);
            condoList.addRoomToCondo(room);
            for(int i = 4; i < data.length; i += 3){
                Person person = new Person(data[i].trim(), data[i+1].trim(), data[i+2].trim());
                room.addPersonToRoom(person);
            }

        }

        reader.close();
    }

    public Condo getCondoListData(){
        try{
            condoList = new Condo();
            readRoomData();
        }catch (FileNotFoundException e){
            System.err.println(this.fileName + " not found");
        }catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return condoList;
    }



    public void setCondoListData(Condo condoList){
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(Room room: condoList.getRooms()){

//                System.out.println(room.getBuildingName()+ " " + room.getFloor() + " " + room.getRoomNumber());
                String line = room.getBuildingName() + ", "
                            + room.getFloor() + ", "
                            + room.getRoomNumber() + ", "
                            + room.getType() ;
                if(!room.toStringToWriteFile().equals("")){
                    line += ", " + room.toStringToWriteFile();
                }
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }


}
