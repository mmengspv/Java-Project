package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import project.java_63.models.AccountBank;
import project.java_63.service.AccountDataSource;
import project.java_63.service.AccountStaffDataSource;

import java.io.IOException;


public class ChangePasswordController {
    @FXML PasswordField passwordField;
    @FXML PasswordField newPasswordField;
    @FXML PasswordField confirmPasswordField;
    @FXML Button changePasswordButton;
    @FXML Button backButton;

    private AccountBank accountBank;
    private AccountDataSource adminAccountDataSource;
    private AccountDataSource residentAccountDataSource;
    private AccountStaffDataSource staffAccountDataSource;

    @FXML public void initialize(){
        adminAccountDataSource = new AccountDataSource("data", "admin_account.csv");
        residentAccountDataSource = new AccountDataSource("data", "account.csv");
        staffAccountDataSource = new AccountStaffDataSource("data", "staff_account.csv");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });

    }

    @FXML public void handleChangePasswordButtonOnAction(ActionEvent event) throws IOException {
        if (passwordField.getText().equals("") || newPasswordField.getText().equals("") || confirmPasswordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not change your password");
            if (passwordField.getText().equals("")) {
                alert.setContentText("Please enter your password");
            } else if (newPasswordField.getText().equals("")) {
                alert.setContentText("Please enter your new password");
            } else if (confirmPasswordField.getText().equals("")) {
                alert.setContentText("Please enter your confirm password");
            }
            alert.showAndWait();
        } else if (accountBank.getCurrentAccount().getPassword().equals(passwordField.getText())) {
            if (newPasswordField.getText().equals(confirmPasswordField.getText())) {
                if(accountBank.getCurrentAccount().getRole().equals("admin")) {
                    accountBank.getCurrentAccount().setPassword(confirmPasswordField.getText());
                    adminAccountDataSource.setAccountData(accountBank);

                    Button changePasswordButton = (Button) event.getSource();
                    Stage stageAdminMenu = (Stage) changePasswordButton.getScene().getWindow();

                    FXMLLoader loaderAdminMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
                    stageAdminMenu.setScene(new Scene(loaderAdminMenu.load(), 800, 600));

                    AdminMenuController adminMenu = loaderAdminMenu.getController();
                    adminMenu.setAccountBank(accountBank);
                    adminAccountDataSource.setAccountData(accountBank);
                    stageAdminMenu.show();
                } else if(accountBank.getCurrentAccount().getRole().equals("resident")){
                    accountBank.getCurrentAccount().setPassword(confirmPasswordField.getText());

                    Button changePasswordButton = (Button) event.getSource();
                    Stage stageResidentMenu = (Stage) changePasswordButton.getScene().getWindow();

                    FXMLLoader loaderResidentMenu = new FXMLLoader(getClass().getResource("/resident_menu.fxml"));
                    stageResidentMenu.setScene(new Scene(loaderResidentMenu.load(), 800, 600));

                    ResidentMenuController residentMenu = loaderResidentMenu.getController();
                    residentMenu.setAccountBank(accountBank);
                    residentAccountDataSource.setAccountData(accountBank);
                    stageResidentMenu.show();
                }else if(accountBank.getCurrentAccount().getRole().equals("staff")){
                    accountBank.getCurrentAccount().setPassword(confirmPasswordField.getText());

                    Button changePasswordButton = (Button) event.getSource();
                    Stage stageStaffMenu = (Stage) changePasswordButton.getScene().getWindow();

                    FXMLLoader loaderStaffMenu = new FXMLLoader(getClass().getResource("/staff_menu.fxml"));
                    stageStaffMenu.setScene(new Scene(loaderStaffMenu.load(), 800,600));

                    StaffMenuController staffMenu = loaderStaffMenu.getController();
                    staffMenu.setAccountBank(accountBank);
                    staffAccountDataSource.setAccountData(accountBank);
                    stageStaffMenu.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not change your password");
                alert.setContentText("Password don't match");
                alert.showAndWait();
            }
        } else if (!accountBank.getCurrentAccount().getPassword().equals(passwordField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not change your password");
            alert.setContentText("Please check your password");
            alert.showAndWait();
        }

    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        if(accountBank.getCurrentAccount().getRole().equals("admin")) {
            Button backButton = (Button) event.getSource();
            Stage stageAdminMenu = (Stage) backButton.getScene().getWindow();

            FXMLLoader loaderAdminMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
            stageAdminMenu.setScene(new Scene(loaderAdminMenu.load(), 800, 600));

            AdminMenuController adminMenu = loaderAdminMenu.getController();
            adminMenu.setAccountBank(accountBank);
            stageAdminMenu.show();
        } else if(accountBank.getCurrentAccount().getRole().equals("resident")){
            Button backButton = (Button)event.getSource();
            Stage stageResidentMenu = (Stage)backButton.getScene().getWindow();

            FXMLLoader loaderResidentMenu = new FXMLLoader(getClass().getResource("/resident_menu.fxml"));
            stageResidentMenu.setScene(new Scene(loaderResidentMenu.load(), 800, 600));

            ResidentMenuController residentMenu = loaderResidentMenu.getController();
            residentMenu.setAccountBank(accountBank);
            stageResidentMenu.show();
        }else if(accountBank.getCurrentAccount().getRole().equals("staff")){
            Button backButton = (Button)event.getSource();
            Stage stageStaffMenu = (Stage) backButton.getScene().getWindow();

            FXMLLoader loaderStaffMenu = new FXMLLoader(getClass().getResource("/staff_menu.fxml"));
            stageStaffMenu.setScene(new Scene(loaderStaffMenu.load(), 800, 600));

            StaffMenuController staffMenu = loaderStaffMenu.getController();
            staffMenu.setAccountBank(accountBank);
            stageStaffMenu.show();
        }
    }

    public void setAccountBank(AccountBank accountBank){
        this.accountBank = accountBank;
    }
}
