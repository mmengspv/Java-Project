package project.java_63.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project.java_63.models.*;
import project.java_63.service.AccountStaffDataSource;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AdminManageStaffController {
    @FXML Button activate;
    @FXML Button backButton;
    @FXML Button deactivate;
    @FXML ImageView image;
    @FXML TableView<AccountStaff> staffTableView;
    @FXML TableColumn<AccountStaff, String> lastLoggedInTableColumn;
    @FXML TableColumn<AccountStaff, String> nameTableColumn;
    @FXML TableColumn<AccountStaff, String> usernameTableColumn;
    @FXML TableColumn<AccountStaff, String> roleTableColumn;
    @FXML TableColumn<AccountStaff, String> statusTableColumn;
    @FXML TableColumn<AccountStaff, String> tryAccessTableColumn;
    @FXML Label nameLabel;
    @FXML Label surnameLabel;
    @FXML Label phoneNumberLabel;
    @FXML Label roleLabel;
    @FXML Label statusLabel;
    @FXML Label tryToAccessLabel;
    @FXML Label usernameLabel;

    private AccountBank accountBank;
    private ArrayList<AccountStaff> accountStaffs;
    private AccountBank accountStaffList;
    private AccountStaff selectedAccountStaff;
    private AccountStaffDataSource accountStaffDataSource;
    private ObservableList<AccountStaff> accountStaffObservableList;

    @FXML public void initialize(){
        accountStaffDataSource = new AccountStaffDataSource("data", "staff_account.csv");
        accountStaffList = accountStaffDataSource.getAccountBank();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
                activate.setDisable(true);
                deactivate.setDisable(true);
                lastLoggedInTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateTime()));
                nameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
                usernameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
                roleTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRole()));
                statusTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBlock()));
                tryAccessTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCountTryToLoginString()));

                showAccountStaffData();
//                Collections.sort(accountStaffObservableList);
//                getMail();

                staffTableView.setItems(accountStaffObservableList);

//                lastLoggedInTableColumn.setSortType(TableColumn.SortType.DESCENDING);
//                staffTableView.getSortOrder().addAll(lastLoggedInTableColumn);

                staffTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        showSelectedStaffAccount(newValue);
                    }
                });
            }
        });
    }


    private void showSelectedStaffAccount(AccountStaff accStaff){
        selectedAccountStaff = accStaff;
        usernameLabel.setText(accStaff.getUsername());
        nameLabel.setText(accStaff.getName());
        surnameLabel.setText(accStaff.getSurname());
        phoneNumberLabel.setText(accStaff.getPhoneNumber());
        roleLabel.setText(accStaff.getRole());
        statusLabel.setText(accStaff.getBlock());
        tryToAccessLabel.setText(accStaff.getCountTryToLoginString());
        File file = new File(accStaff.getImage());
        Image newImage = new Image(file.toURI().toString());
        image.setImage(newImage);
        if(accStaff.getBlock().equals("activate")){
            activate.setDisable(true);
            deactivate.setDisable(false);
        }else{
            activate.setDisable(false);
            deactivate.setDisable(true);
        }

    }

    @FXML public void showAccountStaffData(){
        accountStaffs = new ArrayList<>();
        for(Account acc: accountStaffList.getAccountList()){
            accountStaffs.add((AccountStaff) acc);
        }
        Collections.sort(accountStaffs, (o1, o2) ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            try {
                if(o1.getDate().equals("-") || o2.getDate().equals("-")){
                    return 1;
                }
                Date o1DateIn = simpleDateFormat.parse(o1.getDate());
                Date o2DateIn = simpleDateFormat.parse(o2.getDate());
                if (o1DateIn.getTime() >= o2DateIn.getTime()) return -1;
                return 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        });
        accountStaffObservableList = FXCollections.observableList(accountStaffs);
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageAdminMenu = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
        stageAdminMenu.setScene(new Scene(loaderMenu.load(), 800, 600));
        AdminMenuController adminMenu = loaderMenu.getController();
        adminMenu.setAccountBank(accountBank);
        stageAdminMenu.show();
    }

    private void clearSelectedStaffAccount(){
        selectedAccountStaff = null;
        nameLabel.setText("...");
        surnameLabel.setText("...");
        phoneNumberLabel.setText("...");
        roleLabel.setText("...");
        statusLabel.setText("...");
//        if(newValue.getBlock().equals("activate")){
//            activate.setDisable(true);
//            deactivate.setDisable(false);
//        }else{
//            activate.setDisable(false);
//            deactivate.setDisable(true);
//        }
    }

    @FXML public void handleDeactivateButtonOnAction(ActionEvent event){
        selectedAccountStaff.setBlock("deactivate");
//        clearSelectedStaffAccount();
        statusLabel.setText(selectedAccountStaff.getBlock());
        deactivate.setDisable(true);
        activate.setDisable(false);
        staffTableView.refresh();
        staffTableView.getSelectionModel().clearSelection();
        accountStaffDataSource.setAccountData(accountStaffList);
    }

    @FXML public void handleActivateButtonOnAction(ActionEvent event){
        selectedAccountStaff.setBlock("activate");
//        clearSelectedStaffAccount();
        selectedAccountStaff.setTotalTryToLogin(0);
        statusLabel.setText(selectedAccountStaff.getBlock());
        deactivate.setDisable(false);
        activate.setDisable(true);
        tryToAccessLabel.setText(selectedAccountStaff.getCountTryToLoginString());
        staffTableView.refresh();
        staffTableView.getSelectionModel().clearSelection();
        accountStaffDataSource.setAccountData(accountStaffList);
    }


    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }

}
