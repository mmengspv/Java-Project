package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.java_63.condoinfo.Condo;
import project.java_63.condoinfo.Room;
import project.java_63.models.AccountBank;
import project.java_63.models.Person;
import project.java_63.service.RoomDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AddResidentToRoomController {
    @FXML Button backButton;
    @FXML Button addButton;
    @FXML TextField nameTextField;
    @FXML TextField surnameTextField;
    @FXML TextField phoneNumberTextField;
    @FXML ComboBox<String> buildingComboBox;
    @FXML ComboBox<String> floorComboBox;
    @FXML ComboBox<String> roomNumberComboBox;
    private AccountBank accountBank;
    private RoomDataSource roomDataSource;
    private Condo condoList;

    @FXML public void initialize(){
        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        addItemToBuildingComboBox();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });

    }

    private void addItemToBuildingComboBox(){
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(!data.contains(r.getBuildingName())){
                data.add(r.getBuildingName());
            }
        }
        Collections.sort(data);
        buildingComboBox.getItems().addAll(data);
    }

    @FXML public void handleBuildingNameComboBoxOnAction(ActionEvent event){
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(buildingComboBox.getValue().equals(r.getBuildingName()) && !data.contains(r.getFloor())){
                data.add(r.getFloor());
//                floorComboBox.getItems().add(r.getFloor());
            }
        }
        Collections.sort(data);
        floorComboBox.getItems().addAll(data);
    }

    @FXML public void handleFloorComboBoxOnAction(ActionEvent event){
        roomNumberComboBox.getItems().clear();
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(buildingComboBox.getValue().equals(r.getBuildingName()) && floorComboBox.getValue().equals(r.getFloor())){
                data.add(r.getRoomNumber());
            }
        }
        Collections.sort(data);
        roomNumberComboBox.getItems().addAll(data);
    }



    @FXML public void handleAddButtonOnAction(ActionEvent event) throws IOException {
        if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || phoneNumberTextField.getText().equals("")
            || (buildingComboBox.getValue() == null) || (floorComboBox.getValue() == null) || (roomNumberComboBox.getValue() == null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not add resident to this room");
            if(buildingComboBox.getValue() == null){
                alert.setContentText("Please select building");
            }else if(floorComboBox.getValue() == null){
                alert.setContentText("Please select floor number");
            }else if(roomNumberComboBox.getValue() == null){
                alert.setContentText("Please select room number");
            } else if (nameTextField.getText().equals("")) {
                alert.setContentText("Please enter resident name");
            } else if (surnameTextField.getText().equals("")) {
                alert.setContentText("Please enter resident surname");
            } else if (phoneNumberTextField.getText().equals("")) {
                alert.setContentText("Please enter resident phone number");
            }
            alert.showAndWait();
        }else {
            for (Room r : condoList.getRooms()) {
                if (r.getRoomNumber().equals(roomNumberComboBox.getValue())
                        && buildingComboBox.getValue().equals(r.getBuildingName())
                        && floorComboBox.getValue().equals(r.getFloor())) {
                    if (r.countPersonInRoom() < r.getMaxResident()) {
                        Person person = new Person(nameTextField.getText(), surnameTextField.getText(), phoneNumberTextField.getText());
                        r.addPersonToRoom(person);
                        roomDataSource.setCondoListData(condoList);

                        Button addButton = (Button) event.getSource();
                        Stage stageResidentManagement = (Stage) addButton.getScene().getWindow();

                        FXMLLoader loaderStageResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
                        stageResidentManagement.setScene(new Scene(loaderStageResidentManagement.load(), 800, 600));

                        ResidentManagementController residentManagement = loaderStageResidentManagement.getController();
                        residentManagement.setAccountBank(accountBank);
                        residentManagement.showRoomData();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("Can not add resident to this room");
                        alert.setContentText("This room is already full");
                        alert.showAndWait();
                    }
                }
            }
        }
    }


    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageResidentManagement = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
        stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));

        ResidentManagementController residentManagementController = loaderResidentManagement.getController();
        residentManagementController.setAccountBank(accountBank);
        stageResidentManagement.show();
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
