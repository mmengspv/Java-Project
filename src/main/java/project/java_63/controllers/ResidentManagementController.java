package project.java_63.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class ResidentManagementController {
    @FXML Button backButton;
    @FXML Button removeButton;
    @FXML Button addRoomResidentButton;
    @FXML Button addResidentButton;
    @FXML Button residentInformationButton;
    @FXML TextField removeNameTextField;
    @FXML TextField nameSearchTextField;
    @FXML Label buildingNameLabel;
    @FXML Label floorLabel;
    @FXML Label roomNumberLabel;
    @FXML Label typeLabel;
    @FXML Label residentLabel;
    @FXML ComboBox<String> buildingComboBox;
    @FXML ComboBox<String> floorComboBox;
    @FXML ComboBox<String> roomNumberComboBox;

    @FXML TableView<Room> roomTableView;
    @FXML TableColumn<Room, String> buildingNameTableColumn;
    @FXML TableColumn<Room, String> floorTableColumn;
    @FXML TableColumn<Room, String> roomNumberTableColumn;
    @FXML TableColumn<Room, String> typeTableColumn;
    @FXML TableColumn<Room, String> hostTableColumn;
    @FXML TableColumn<Room, String> totalResidentTableColumn;

    private AccountBank accountBank;
    private Condo condoList;
    private Room selectedRoom;
    private RoomDataSource roomDataSource;
    private ObservableList<Room> roomObservableList;


    @FXML public void initialize(){
        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
                buildingNameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBuildingName()));
                floorTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFloor()));
                roomNumberTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
                typeTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
                hostTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().toStringToTableColumn()));
                totalResidentTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().toStringResidentInRoom()));
                showRoomData();
//                showBuildingComboBoxData();

                roomTableView.setItems(roomObservableList);
                roomTableView.setItems(searchName());

                roomTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        showSelectedRoom(newValue);
                    }
                });
            }
        });
    }

    public SortedList<Room> searchName() {
        FilteredList<Room> roomFilteredList = new FilteredList<>(roomObservableList, p -> true);
        nameSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            roomFilteredList.setPredicate(room -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                for (Person person : room.getPersons()) {
                    if ((person.getName().toUpperCase() + " " + person.getSurname().toUpperCase()).contains(newValue.toUpperCase()) ||person.getName().toUpperCase().contains(newValue.toUpperCase()) || person.getSurname().toUpperCase().contains(newValue.toUpperCase())){
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<Room> roomSortedList = new SortedList<>(roomFilteredList);
        roomSortedList.comparatorProperty().bind(roomTableView.comparatorProperty());
        return roomSortedList;
    }

//    private void showBuildingComboBoxData(){
//        ArrayList<String> data = new ArrayList<>();
//        for(Room r: condoList.getRooms()){
//            if(!data.contains(r.getBuildingName())){
//                data.add(r.getBuildingName());
//            }
//        }
//        Collections.sort(data);
//        buildingComboBox.getItems().addAll(data);
//    }

//    @FXML public void handleBuildingComboBoxOnAction(){
//        ArrayList<String> data = new ArrayList<>();
//        for(Room r: condoList.getRooms()){
//            if(r.getBuildingName().equals(buildingComboBox.getValue()) && !data.contains(r.getFloor())){
//                data.add(r.getFloor());
//            }
//        }
//        Collections.sort(data);
//        floorComboBox.getItems().addAll(data);
//    }

//    @FXML public void handleFloorComboBoxOnAction(){
//        roomNumberComboBox.getItems().clear();
//        ArrayList<String> data = new ArrayList<>();
//        for(Room r: condoList.getRooms()){
//            if(r.getBuildingName().equals(buildingComboBox.getValue()) && r.getFloor().equals(floorComboBox.getValue())
//                && !data.contains(roomNumberComboBox.getValue())){
//                data.add(r.getRoomNumber());
//            }
//        }
//        Collections.sort(data);
//        roomNumberComboBox.getItems().addAll(data);
//    }

    @FXML private void showSelectedRoom(Room room){
        selectedRoom = room;
//        buildingComboBox.setValue(room.getBuildingName());
//        floorComboBox.setValue(room.getFloor());
//        roomNumberComboBox.setValue(room.getRoomNumber());
        buildingNameLabel.setText(room.getBuildingName());
        floorLabel.setText(room.getFloor());
        roomNumberLabel.setText(room.getRoomNumber());
        typeLabel.setText(room.getType());
        residentLabel.setText(room.toStringPerson());

    }

    @FXML public void showRoomData(){
        ArrayList<Room> tmpList = (ArrayList<Room>)condoList.getRooms().clone();
        Collections.sort(tmpList, (o1, o2) -> {
            if (Integer.parseInt(o1.getRoomNumber()) > Integer.parseInt(o2.getRoomNumber())) return 1;
            return -1;
        });
        roomObservableList = FXCollections.observableList(tmpList);
//        roomObservableList = FXCollections.observableList(condoList.getRooms());
    }

//    @FXML public void handleRemoveButtonOnAction(ActionEvent event) throws IOException {
//        if((buildingComboBox.getValue() == null) || (floorComboBox.getValue() == null)
//                || (roomNumberComboBox.getValue() == null) || removeNameTextField.getText().equals("")){
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setHeaderText("");
//            alert.setTitle("Can not remove resident out of room");
//            if(buildingComboBox.getValue() == null){
//                alert.setContentText("Please select building");
//            }else if(floorComboBox.getValue() == null){
//                alert.setContentText("Please select floor number");
//            }else if(roomNumberComboBox.getValue() == null){
//                alert.setContentText("Please select room number");
//            }else if(removeNameTextField.getText().equals("")){
//                alert.setContentText("Please enter name resident to remove");
//            }
//            alert.showAndWait();
//        }else{
//            for(Room r: condoList.getRooms()){
//                if(buildingComboBox.getValue().equals(r.getBuildingName()) && floorComboBox.getValue().equals(r.getFloor())
//                    && roomNumberComboBox.getValue().equals(r.getRoomNumber())){
//                    if(r.checkPerson(removeNameTextField.getText())){
//                        r.getPersons().remove(r.getCurrentPerson());
//                        roomDataSource.setCondoListData(condoList);
//                        Button removeNameButton = (Button) event.getSource();
//                        Stage stageResidentManagement = (Stage)removeNameButton.getScene().getWindow();
//
//                        FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
//                        stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));
//
//                        ResidentManagementController residentManage = loaderResidentManagement.getController();
//                        residentManage.setAccountBank(accountBank);
//
//                        stageResidentManagement.show();
//                    }else{
//                        Alert alert = new Alert(Alert.AlertType.WARNING);
//                        alert.setHeaderText("");
//                        alert.setTitle("Can not remove resident of this room");
//                        alert.setContentText("Please check resident name");
//                        alert.showAndWait();
//                    }
//                }
//            }
//        }
//    }


    @FXML public void handleResidentInformationButtonOnAction(ActionEvent event) throws IOException {
        Button residentInformationButton = (Button) event.getSource();
        Stage stageResidentInformation = (Stage) residentInformationButton.getScene().getWindow();

        FXMLLoader loaderResidentInformation = new FXMLLoader(getClass().getResource("/resident_information.fxml"));
        stageResidentInformation.setScene(new Scene(loaderResidentInformation.load(), 800, 600));

        ResidentInformationController residentInformationController = loaderResidentInformation.getController();
        residentInformationController.setAccountBank(accountBank);
        stageResidentInformation.show();
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageStaffMenu = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderStageStaffMenu = new FXMLLoader(getClass().getResource("/staff_menu.fxml"));
        stageStaffMenu.setScene(new Scene(loaderStageStaffMenu.load(), 800, 600));

        StaffMenuController staffMenu = loaderStageStaffMenu.getController();
        staffMenu.setAccountBank(accountBank);
        stageStaffMenu.show();
    }

    @FXML public void handleAddRoomResidentButtonOnAction(ActionEvent event) throws IOException {
        Button addRoomResidentButton = (Button) event.getSource();
        Stage stageAddRoom = (Stage) addRoomResidentButton.getScene().getWindow();

        FXMLLoader loaderAddRoom = new FXMLLoader(getClass().getResource("/add_room.fxml"));
        stageAddRoom.setScene(new Scene(loaderAddRoom.load(), 800, 600));

        AddRoomController addRoomController = loaderAddRoom.getController();
        addRoomController.setAccountBank(accountBank);
        stageAddRoom.show();
    }

    @FXML public void handleAddResidentButtonOnAction(ActionEvent event) throws IOException {
        Button addResidentButton = (Button) event.getSource();
        Stage stageAddResident = (Stage)addResidentButton.getScene().getWindow();

        FXMLLoader loaderAddResident = new FXMLLoader(getClass().getResource("/add_resident_to_room.fxml"));
        stageAddResident.setScene(new Scene(loaderAddResident.load(), 800, 600));

        AddResidentToRoomController addResidentToRoomController = loaderAddResident.getController();
        addResidentToRoomController.setAccountBank(accountBank);
        stageAddResident.show();
    }

    public void setAccountBank(AccountBank accountBank){
        this.accountBank = accountBank;
    }
}
