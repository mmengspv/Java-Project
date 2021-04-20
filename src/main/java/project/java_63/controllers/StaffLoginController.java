package project.java_63.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.java_63.models.AccountBank;
import project.java_63.models.AccountStaff;
import project.java_63.service.AccountStaffDataSource;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffLoginController {
    private @FXML Button loginButton;
    private @FXML Button backButton;
    private @FXML TextField userNameStaffTextField;
    private @FXML PasswordField passwordStaffPasswordField;
    private AccountBank accountBank;
    private AccountStaffDataSource accountStaffDataSource;

    @FXML public void initialize(){
        accountStaffDataSource = new AccountStaffDataSource("data", "staff_account.csv");
        accountBank = accountStaffDataSource.getAccountBank();
    }


    @FXML public void handleLoginButtonOnAction(ActionEvent event) throws IOException {
        System.out.println(userNameStaffTextField.getText());
        System.out.println(passwordStaffPasswordField.getText());

        if (accountBank.checkUser(userNameStaffTextField.getText(), passwordStaffPasswordField.getText())) {
            AccountStaff accStaff = (AccountStaff) accountBank.getCurrentAccount();
            if (accStaff.getBlock().equals("activate")) {
                Button loginButton = (Button) event.getSource();
                Stage staffMenuStage = (Stage) loginButton.getScene().getWindow();

                FXMLLoader loaderStaffMenuStage = new FXMLLoader(getClass().getResource("/staff_menu.fxml"));
                staffMenuStage.setScene(new Scene(loaderStaffMenuStage.load(), 800, 600));

                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatTime = date.format(dateFormat);


                StaffMenuController staffMenu = loaderStaffMenuStage.getController();
                staffMenu.setAccountBank(accountBank);

                accountBank.getCurrentAccount().setDate(formatTime);
                accountStaffDataSource.setAccountData(accountBank);
                staffMenuStage.show();
            } else if (accStaff.getBlock().equals("deactivate")) {
                accStaff.countTryToLogin();
                accountStaffDataSource.setAccountData(accountBank);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not login");
                alert.setContentText("Your account has been blocked from the server");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not login");
            alert.setContentText("Please check your username and password");
            alert.showAndWait();
        }
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageController = (Stage)backButton.getScene().getWindow();

        FXMLLoader loaderStage = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageController.setScene(new Scene(loaderStage.load(), 800, 600));

        stageController.show();
    }
}
