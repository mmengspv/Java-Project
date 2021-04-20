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
import project.java_63.service.RoomDataSource;

import java.io.IOException;

public class AddRoomController {
    @FXML Button backButton;
    @FXML Button addRoomButton;
    @FXML ComboBox<String> buildingComboBox;
    @FXML ComboBox<String> floorComboBox;
    @FXML ComboBox<String> roomTypeComboBox;
    @FXML TextField roomNumberTextField;
    private AccountBank accountBank;
    private RoomDataSource roomDataSource;
    private Condo condoList;

    @FXML public void initialize(){
        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        buildingComboBox.getItems().add("A");
        floorComboBox.getItems().addAll("1","2","3","4","5","6","7","8");
        roomTypeComboBox.getItems().addAll("Studio", "One Bedroom", "Two Bedrooms");

        floorComboBox.setDisable(true);
        roomTypeComboBox.setDisable(true);
        roomNumberTextField.setDisable(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });

    }

    @FXML public void handleBuildingComboBoxOnAction(){
        floorComboBox.setDisable(false);
        floorComboBox.setValue("1");
    }

    @FXML public void handleFloorComboBoxOnAction(ActionEvent event){
        roomNumberTextField.setText(condoList.toString_NextRoom(buildingComboBox.getValue(), floorComboBox.getValue()));
        System.out.println(buildingComboBox.getValue());
        System.out.println(floorComboBox.getValue());
        roomTypeComboBox.setDisable(false);
    }


    @FXML public void handleAddRoomButtonOnAction(ActionEvent event) throws IOException {
        if((buildingComboBox.getValue() == null) || (roomTypeComboBox.getValue() == null)
                || (floorComboBox.getValue() == null)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not add rooms");
            if(buildingComboBox.getValue() == null){
                alert.setContentText("Please select the building");
            }else if(floorComboBox.getValue() == null){
                alert.setContentText("Please select floor number");
            }else if(roomTypeComboBox.getValue() == null){
                alert.setContentText("Please select room type");
            }
            alert.showAndWait();
        }else {
            Room room = new Room(buildingComboBox.getValue(), floorComboBox.getValue(), roomNumberTextField.getText(), roomTypeComboBox.getValue());
            condoList.addRoomToCondo(room);
            roomDataSource.setCondoListData(condoList);

            Button addRoomButton = (Button) event.getSource();
            Stage stageResidentManagement = (Stage) addRoomButton.getScene().getWindow();

            FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
            stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));
//        System.out.println(buildingComboBox.getValue() + " " + floorComboBox.getValue() + " " + roomNumberTextField.getText() + " " + roomTypeComboBox.getValue());

            ResidentManagementController residentManagementController = loaderResidentManagement.getController();
            residentManagementController.setAccountBank(accountBank);
            stageResidentManagement.show();
        }
    }



    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageResidentManagement = (Stage)backButton.getScene().getWindow();

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
