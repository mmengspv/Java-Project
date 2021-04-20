package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.java_63.condoinfo.Condo;
import project.java_63.condoinfo.Room;
import project.java_63.models.AccountBank;
import project.java_63.models.Person;
import project.java_63.service.RoomDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ResidentInformationController {
    @FXML Button backButton;
    @FXML Button editButton;
    @FXML ComboBox<String> buildingComboBox;
    @FXML ComboBox<String> floorComboBox;
    @FXML ComboBox<String> roomNumberComboBox;
    @FXML TextField nameTextField;
    @FXML TextField surnameTextField;
    @FXML TextField phoneNumberTextField;
    @FXML ComboBox<String> residentComboBox;
    private AccountBank accountBank;
    private RoomDataSource condoDataSource;
    private Condo condoList;

    public void initialize(){
        condoDataSource = new RoomDataSource("data", "room.csv");
        condoList = condoDataSource.getCondoListData();
        showBuildingComboBox();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });

    }

    private void showBuildingComboBox(){
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(!data.contains(r.getBuildingName())){
                data.add(r.getBuildingName());
            }
        }
        Collections.sort(data);
        buildingComboBox.getItems().addAll(data);
    }

    @FXML public void handleBuildingComboBoxOnAction(){
        floorComboBox.getItems().clear();
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(r.getBuildingName().equals(buildingComboBox.getValue()) && !data.contains(r.getFloor())){
                data.add(r.getFloor());
            }
        }
        Collections.sort(data);
        floorComboBox.getItems().addAll(data);
    }

    @FXML public void handleFloorComboBoxOnAction(){
        roomNumberComboBox.getItems().clear();
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(r.getBuildingName().equals(buildingComboBox.getValue()) && r.getFloor().equals(floorComboBox.getValue())
                    && !data.contains(roomNumberComboBox.getValue())){
                data.add(r.getRoomNumber());
            }
        }
        Collections.sort(data);
        roomNumberComboBox.getItems().addAll(data);
    }

    @FXML public void handleRoomNumberComboBoxOnAction(){
        residentComboBox.getItems().clear();
        if(condoList.checkRoom(roomNumberComboBox.getValue())){
            for(Person p: condoList.getCurrentRoom().getPersons()){
                residentComboBox.getItems().add(p.getName()+" "+p.getSurname());
            }
        }
    }

    @FXML public void handleResidentComboBoxOnAction(){
        if(residentComboBox.getValue() != null) {
            String[] name = residentComboBox.getValue().split(" ");
            nameTextField.setText(name[0].trim());
            surnameTextField.setText(name[1].trim());
            for (Person p : condoList.getCurrentRoom().getPersons()) {
                if (p.getName().equals(name[0].trim()) && p.getSurname().equals(name[1].trim())) {
                    phoneNumberTextField.setText(p.getPhoneNumber());
                }
            }
        }else{
            nameTextField.clear();
            surnameTextField.clear();
            phoneNumberTextField.clear();
        }
    }

    @FXML public void handleRemoveButtonOnAction(ActionEvent event) throws IOException {
        if(residentComboBox.getValue() != null) {
            if (condoList.checkRoom(roomNumberComboBox.getValue())) {
                String name[] = residentComboBox.getValue().split(" ");
                for (Person p : condoList.getCurrentRoom().getPersons()) {
                    if (p.getName().equals(name[0].trim()) && p.getSurname().equals(name[1].trim())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("");
                        alert.setTitle("Remove resident");
                        alert.setContentText("Confirm to remove this resident");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get()  == ButtonType.OK){
                            condoList.getCurrentRoom().getPersons().remove(p);
                            condoDataSource.setCondoListData(condoList);

                            Button removeButton = (Button) event.getSource();
                            Stage stageResidentManagement = (Stage) removeButton.getScene().getWindow();

                            FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
                            stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));

                            ResidentManagementController residentManagementController = loaderResidentManagement.getController();
                            residentManagementController.setAccountBank(accountBank);
                            stageResidentManagement.show();
                            break;
                        }
                    }
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not remove resident out of room");
            if(buildingComboBox.getValue() == null){
                alert.setContentText("Please select building name");
            }else if(floorComboBox.getValue() == null){
                alert.setContentText("Please select floor number");
            }else if(roomNumberComboBox.getValue() == null){
                alert.setContentText("Please select room number");
            }else if(residentComboBox.getValue() == null) {
                alert.setContentText("Please select resident in this room");
            }
            alert.showAndWait();
        }
    }

    @FXML public void handleEditButtonOnAction(ActionEvent event) throws IOException {
        if (residentComboBox.getValue() != null) {
            String name[] = residentComboBox.getValue().split(" ");
            if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || phoneNumberTextField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not edit information resident");
                if(nameTextField.getText().equals("")){
                    alert.setContentText("Please enter the name of the resident for editing");
                }else if(surnameTextField.getText().equals("")){
                    alert.setContentText("Please enter the surname of the resident for editing");
                }else if(phoneNumberTextField.getText().equals("")){
                    alert.setContentText("Please enter phone number of the resident for editing");
                }
                alert.showAndWait();
            }else {
                if(condoList.checkRoom(roomNumberComboBox.getValue())){
                    for(Person p: condoList.getCurrentRoom().getPersons()){
                        if(p.getName().equals(name[0].trim()) && p.getSurname().equals(name[1].trim())){
                            p.setName(nameTextField.getText());
                            p.setSurname(surnameTextField.getText());
                            p.setPhoneNumber(phoneNumberTextField.getText());
                        }
                    }
                }
                condoDataSource.setCondoListData(condoList);

                Button editButton = (Button) event.getSource();
                Stage stageResidentManagement = (Stage)editButton.getScene().getWindow();

                FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
                stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));

                ResidentManagementController residentManagementController = loaderResidentManagement.getController();
                residentManagementController.setAccountBank(accountBank);
                stageResidentManagement.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("The resident information cannot be edited");
            alert.setContentText("Please select resident to edit");
            alert.showAndWait();
        }
    }


    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button)event.getSource();
        Stage stageResidentManagement = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderStageResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
        stageResidentManagement.setScene(new Scene(loaderStageResidentManagement.load(), 800, 600));

        ResidentManagementController residentManagementController = loaderStageResidentManagement.getController();
        residentManagementController.setAccountBank(accountBank);
        stageResidentManagement.show();
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
