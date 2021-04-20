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


public class AdminLoginController {
    @FXML Button loginButton;
    @FXML Button backButton;
    @FXML TextField usernameTextField;
    @FXML PasswordField passwordField;
    private AccountBank accountBank;
    private AccountDataSource accountDataSource;

    @FXML public void initialize(){
        accountDataSource = new AccountDataSource("data", "admin_account.csv");
        accountBank = accountDataSource.getAccountData();
//        accountBank.getAccountList().forEach( i ->{
//            System.out.println(i);
//        });

    }

    @FXML public void handleLoginButtonOnAction(ActionEvent event) throws IOException {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordField.getText());
//        Collections.sort(accountBank.getAccountList());
        if(accountBank.checkUser(usernameTextField.getText(), passwordField.getText())) {
            Button loginButton = (Button) event.getSource();
            Stage stageAdminMenu = (Stage) loginButton.getScene().getWindow();

            FXMLLoader loaderAdminMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
            stageAdminMenu.setScene(new Scene(loaderAdminMenu.load(), 800, 600));

            AdminMenuController adminMenu = loaderAdminMenu.getController();
            adminMenu.setAccountBank(accountBank);

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatTime = date.format(dateFormat);


            accountBank.getCurrentAccount().setDate(formatTime);
            accountDataSource.setAccountData(accountBank);
            stageAdminMenu.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not login");
            alert.setContentText("Please check your username and password");
            alert.showAndWait();
        }
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageWelcome = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderWelcome = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageWelcome.setScene(new Scene(loaderWelcome.load(), 800, 600));


        stageWelcome.show();
    }
}
