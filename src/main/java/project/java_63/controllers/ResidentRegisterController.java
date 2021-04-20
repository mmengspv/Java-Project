package project.java_63.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.java_63.condoinfo.Condo;
import project.java_63.condoinfo.Room;
import project.java_63.models.Account;
import project.java_63.models.AccountBank;
import project.java_63.models.Person;
import project.java_63.service.AccountDataSource;
import project.java_63.service.AccountStaffDataSource;
import project.java_63.service.RoomDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ResidentRegisterController {
    @FXML Button backButton;
    @FXML Button registerButton;
    @FXML TextField nameTextField;
    @FXML TextField surnameTextField;
    @FXML TextField usernameTextField;
    @FXML PasswordField passwordField;
    @FXML PasswordField confirmPasswordField;
    @FXML ComboBox<String> buildingComboBox;
    @FXML ComboBox<String> floorComboBox;
    @FXML ComboBox<String> roomNumberComboBox;

    private AccountBank accountBank;
    private AccountDataSource accountDataSource;
    private RoomDataSource roomDataSource;
    private Condo condoList;
    private AccountStaffDataSource accountStaffDataSource;
    private AccountBank staffAccountList;
    private AccountDataSource adminAccountDataSource;
    private AccountBank adminAccountBank;

    @FXML public void initialize(){
        accountDataSource = new AccountDataSource("data", "account.csv");
        accountBank = accountDataSource.getAccountData();

        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        accountStaffDataSource = new AccountStaffDataSource("data", "staff_account.csv");
        staffAccountList = accountStaffDataSource.getAccountBank();

       adminAccountDataSource = new AccountDataSource("data", "admin_account.csv");
       adminAccountBank = adminAccountDataSource.getAccountData();



        showBuildingComboBoxData();

    }

    private void showBuildingComboBoxData(){
        buildingComboBox.getItems().clear();
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(!data.contains(r.getBuildingName())){
                data.add(r.getBuildingName());
            }
        }
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
        floorComboBox.getItems().addAll(data);
    }

    @FXML public void handleFloorComboBoxOnAction(){
        roomNumberComboBox.getItems().clear();
        ArrayList<String> data = new ArrayList<>();
        for(Room r: condoList.getRooms()){
            if(r.getBuildingName().equals(buildingComboBox.getValue()) && r.getFloor().equals(floorComboBox.getValue())
                && !data.contains(r.getRoomNumber())){
                data.add(r.getRoomNumber());
            }
        }
        roomNumberComboBox.getItems().addAll(data);
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button)event.getSource();
        Stage stageResidentLogin = (Stage)backButton.getScene().getWindow();

        FXMLLoader loaderStageResidentLogin = new FXMLLoader(getClass().getResource("/resident_login.fxml"));
        stageResidentLogin.setScene(new Scene(loaderStageResidentLogin.load(), 800, 600));

        stageResidentLogin.show();
    }

    @FXML public void handleRegisterButtonOnAction(ActionEvent event) throws IOException {
        if(nameTextField.getText().equals("") || surnameTextField.getText().equals("") || usernameTextField.getText().equals("")
            || passwordField.getText().equals("") || confirmPasswordField.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not register");
            if(nameTextField.getText().equals("")){
                alert.setContentText("Please enter your name");
            }else if(surnameTextField.getText().equals("")){
                alert.setContentText("Please enter your surname");
            }else if(usernameTextField.getText().equals("")){
                alert.setContentText("Please enter username");
            }else if(passwordField.getText().equals("")){
                alert.setContentText("Please enter password");
            }else if(confirmPasswordField.getText().equals("")){
                alert.setContentText("Please enter confirm password");
            }
            alert.showAndWait();
        }else{
            if((buildingComboBox.getValue() == null) || (floorComboBox.getValue() == null) || (roomNumberComboBox.getValue() == null)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not register");
                if(buildingComboBox.getValue() == null){
                    alert.setContentText("Please select building name");
                }else if(floorComboBox.getValue() == null){
                    alert.setContentText("Please select floor number");
                }else if(roomNumberComboBox.getValue() == null){
                    alert.setContentText("Please select room number");
                }
                alert.showAndWait();
            }else {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    if (accountBank.findUserName(usernameTextField.getText()) || staffAccountList.findUserName(usernameTextField.getText()) || adminAccountBank.findUserName(usernameTextField.getText())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("");
                        alert.setTitle("Can not register");
                        alert.setContentText("This username is already used");
                        alert.showAndWait();
                    } else {
                        if (condoList.checkRoom(roomNumberComboBox.getValue())) {
                            int i = 0;
                            for (Person p : condoList.getCurrentRoom().getPersons()) {
                                if (p.getName().equals(nameTextField.getText()) && p.getSurname().equals(surnameTextField.getText())) {
                                    i += 1;
                                    Account account = new Account(usernameTextField.getText(), passwordField.getText(), nameTextField.getText(), surnameTextField.getText(),
                                            p.getPhoneNumber(), "resident");
                                    account.setDate("-");
                                    accountBank.addAccount(account);
                                    accountDataSource.setAccountData(accountBank);

                                    Button registerButton = (Button) event.getSource();
                                    Stage stageResidentLogin = (Stage) registerButton.getScene().getWindow();

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident_login.fxml"));
                                    stageResidentLogin.setScene(new Scene(loader.load(), 800, 600));
                                    stageResidentLogin.show();

                                }
                            }
                            if(i == 0) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("");
                                alert.setTitle("Can not register");
                                alert.setContentText("Don't have this name in the room\nPlease check your name and surname");
                                alert.showAndWait();
                            }
                        }
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("");
                    alert.setTitle("Can not register");
                    alert.setContentText("Password and confirm password not match\n Please check you password and confirm password");
                    alert.showAndWait();
                }
            }
        }
    }


    public void setAccountBank(AccountBank accountBank){
        this.accountBank = accountBank;
    }
}
