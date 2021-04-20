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
import project.java_63.service.AccountDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResidentLoginController {
    @FXML private Button loginButton, registerButton, backButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private AccountBank accountBank;
    private AccountDataSource accHardCode;


    @FXML public void initialize(){
        accHardCode = new AccountDataSource("data", "account.csv");
        accountBank = accHardCode.getAccountData();



    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageWelcome = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderWelcome = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageWelcome.setScene(new Scene(loaderWelcome.load(), 800, 600));

//        stageWelcome.getScene()
//                .getStylesheets()
//                .add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stageWelcome.show();
    }

    @FXML public void handleRegisterButtonOnAction(ActionEvent event) throws IOException {
        Button registerButton = (Button) event.getSource();
        Stage stageRegister = (Stage) registerButton.getScene().getWindow();

        FXMLLoader loaderStageResident = new FXMLLoader(getClass().getResource("/resident_register.fxml"));
        stageRegister.setScene(new Scene(loaderStageResident.load(), 800, 600));

        stageRegister.show();
    }

    @FXML public void handleLoginButtonOnAction(ActionEvent event) throws IOException {
        System.out.println("Username: "+usernameField.getText());
        System.out.println("Password: "+passwordField.getText());

        if(accountBank.checkUser(usernameField.getText(), passwordField.getText())) {
            Button loginButton = (Button) event.getSource();
            Stage stageResidentMenu = (Stage) loginButton.getScene().getWindow();

            FXMLLoader loaderResidentMenu = new FXMLLoader(getClass().getResource("/resident_menu.fxml"));
            stageResidentMenu.setScene(new Scene(loaderResidentMenu.load(), 800, 600));

            ResidentMenuController rsMenu = loaderResidentMenu.getController();
            rsMenu.setAccountBank(accountBank);

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatTime = date.format(dateFormat);

            accountBank.getCurrentAccount().setDate(formatTime);
            accHardCode.setAccountData(accountBank);
            stageResidentMenu.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not login");
            alert.setContentText("Please check your username and password");
            alert.showAndWait();
        }
    }



}
