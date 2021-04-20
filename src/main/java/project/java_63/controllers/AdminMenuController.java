package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import project.java_63.models.AccountBank;


import java.io.IOException;

public class AdminMenuController {
    private AccountBank accountBank;
    @FXML Button logoutButton;
    @FXML Button createStaffAccountButton;
    @FXML Button manageStaffAccountButton;
    @FXML Button changePasswordButton;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });
    }

    @FXML public void handleLogoutButtonOnAction(ActionEvent event) throws IOException {
        Button logoutButton = (Button)event.getSource();
        Stage stageWelcome = (Stage) logoutButton.getScene().getWindow();

        FXMLLoader loaderWelcome = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageWelcome.setScene(new Scene(loaderWelcome.load(), 800,600));

        stageWelcome.show();
    }

    @FXML public void handleManageStaffAccountButtonOnAction(ActionEvent event) throws IOException {
        Button manageStaffAccountButton = (Button)event.getSource();
        Stage stageManageAccount = (Stage)manageStaffAccountButton.getScene().getWindow();

        FXMLLoader loaderManageAccount = new FXMLLoader(getClass().getResource("/admin_manage_staff.fxml"));
        stageManageAccount.setScene(new Scene(loaderManageAccount.load(), 800,600));

        AdminManageStaffController adminManageStaff = loaderManageAccount.getController();
        adminManageStaff.setAccountBank(accountBank);

        stageManageAccount.show();
    }

    @FXML public void handleCreateStaffAccountButtonOnAction(ActionEvent event) throws IOException {
        Button createStaffAccountButton = (Button)event.getSource();
        Stage stageCreateStaffAccount = (Stage)createStaffAccountButton.getScene().getWindow();

        FXMLLoader loaderStageCreateStaffAccount = new FXMLLoader(getClass().getResource("/create_staff_account.fxml"));
        stageCreateStaffAccount.setScene(new Scene(loaderStageCreateStaffAccount.load(), 800,600));

        CreateStaffAccountController createStaff = loaderStageCreateStaffAccount.getController();
        createStaff.setAccountBank(accountBank);
        stageCreateStaffAccount.show();
    }

    @FXML public void handleChangePasswordButtonOnAction(ActionEvent event) throws IOException {
        Button changePasswordButton = (Button) event.getSource();
        Stage stageChangePassword = (Stage) changePasswordButton.getScene().getWindow();

        FXMLLoader loaderChangePassword = new FXMLLoader(getClass().getResource("/change_password.fxml"));
        stageChangePassword.setScene(new Scene(loaderChangePassword.load(), 800,600));

        ChangePasswordController changePassword = loaderChangePassword.getController();
        changePassword.setAccountBank(accountBank);
        stageChangePassword.show();
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
