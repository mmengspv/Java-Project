package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import project.java_63.controllers.ChangePasswordController;
import project.java_63.controllers.ResidentManagementController;
import project.java_63.controllers.StaffMailboxManagementController;
import project.java_63.models.AccountBank;

import java.io.IOException;

public class StaffMenuController {
    @FXML Button logoutButton;
    @FXML Button changePasswordButton;
    @FXML Button residentManagementButton;
    @FXML Button mailboxManagementButton;

    private AccountBank accountBank;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });
    }

    @FXML public void handleLogoutButtonOnAction(ActionEvent event) throws IOException {
        Button logoutButton = (Button) event.getSource();
        Stage stageWelcome = (Stage) logoutButton.getScene().getWindow();

        FXMLLoader loaderWelcome = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageWelcome.setScene(new Scene(loaderWelcome.load(), 800, 600));

        stageWelcome.show();
    }

    @FXML public void handleResidentManagementButtonOnAction(ActionEvent event) throws IOException {
        Button residentManagementButton = (Button) event.getSource();
        Stage stageResidentManagement = (Stage)residentManagementButton.getScene().getWindow();

        FXMLLoader loaderResidentManagement = new FXMLLoader(getClass().getResource("/resident_management.fxml"));
        stageResidentManagement.setScene(new Scene(loaderResidentManagement.load(), 800, 600));

        ResidentManagementController residentManagement = loaderResidentManagement.getController();
        residentManagement.setAccountBank(accountBank);
        stageResidentManagement.show();
    }

    @FXML public void handleChangePasswordButtonOnAction(ActionEvent event) throws IOException {
        Button changePasswordButton = (Button) event.getSource();
        Stage stageChangePassword = (Stage) changePasswordButton.getScene().getWindow();

        FXMLLoader loaderChangePassword = new FXMLLoader(getClass().getResource("/change_password.fxml"));
        stageChangePassword.setScene(new Scene(loaderChangePassword.load(), 800, 600));

        ChangePasswordController changePassword = loaderChangePassword.getController();
        changePassword.setAccountBank(accountBank);
        stageChangePassword.show();
    }

    @FXML public void handleMailboxManagementButtonOnAction(ActionEvent event) throws IOException {
        Button mailboxManagementButton = (Button) event.getSource();
        Stage stageMailboxManagement = (Stage)mailboxManagementButton.getScene().getWindow();

        FXMLLoader loaderStageMailboxManagement = new FXMLLoader(getClass().getResource("/staff_mailbox_management.fxml"));
        stageMailboxManagement.setScene(new Scene(loaderStageMailboxManagement.load(), 800, 600));

        StaffMailboxManagementController staffMailboxManagementController = loaderStageMailboxManagement.getController();
        staffMailboxManagementController.setAccountBank(accountBank);
        stageMailboxManagement.show();
    }

    public void setAccountBank(AccountBank accountBank){
        this.accountBank = accountBank;
    }
}
