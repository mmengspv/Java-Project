package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.java_63.controllers.AdminMenuController;
import project.java_63.models.AccountBank;
import project.java_63.models.AccountStaff;
import project.java_63.service.AccountStaffDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class CreateStaffAccountController {
    @FXML Button backButton;
    @FXML Button createAccountButton;
    @FXML Button uploadButton;
    @FXML TextField uploadTextField;
    @FXML ImageView image;
    @FXML TextField nameTextField;
    @FXML TextField surnameTextField;
    @FXML TextField usernameTextField;
    @FXML TextField phoneNumberTextField;
    @FXML PasswordField passwordField;
    @FXML PasswordField confirmPasswordField;

    private File imageFile;
    private AccountBank accountBank;
    private AccountBank accountStaffList;
    private AccountStaffDataSource accountStaffDataSource;
    private String imageTarget;


    @FXML public void initialize(){
        accountStaffDataSource = new AccountStaffDataSource("data", "staff_account.csv");
        accountStaffList = accountStaffDataSource.getAccountBank();
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                // SET FILECHOOSER INITIAL DIRECTORY
                chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                // DEFINE ACCEPTABLE FILE EXTENSION
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg"));
                // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
                imageFile = chooser.showOpenDialog(uploadButton.getScene().getWindow());
                if (imageFile != null){
                    try {
                        // CREATE FOLDER IF NOT EXIST
                        File destDir = new File("data/staff_images");
                        destDir.mkdirs();
                        // RENAME FILE
                        String[] fileSplit = imageFile.getName().split("\\.");
                        String filename = LocalDate.now()+"_"+System.currentTimeMillis()+"."+fileSplit[fileSplit.length - 1];
                        Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+filename);
                        imageTarget = "data" + "/" + "staff_images" + "/" + filename;
                        // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                        Files.copy(imageFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                        // SET NEW FILE PATH TO IMAGE
                        image.setImage(new Image(target.toUri().toString()));
                        uploadTextField.setText(imageFile.toPath().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });
    }


    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageAdminMenu = (Stage) backButton.getScene().getWindow();

        FXMLLoader loaderAdminMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
        stageAdminMenu.setScene(new Scene(loaderAdminMenu.load(), 800,600));

        AdminMenuController adminMenu = loaderAdminMenu.getController();
        adminMenu.setAccountBank(accountBank);
        stageAdminMenu.show();
    }

    @FXML public void handleCreateAccountButtonOnAction(ActionEvent event) throws IOException {
        if(nameTextField.getText().equals("") || surnameTextField.getText().equals("")
                || usernameTextField.getText().equals("") || passwordField.getText().equals("")
                || confirmPasswordField.getText().equals("") || phoneNumberTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not create staff account");
            if(nameTextField.getText().equals("")){
                alert.setContentText("Please enter your name");
            }else if(surnameTextField.getText().equals("")){
                alert.setContentText("Please enter your surname");
            }else if(usernameTextField.getText().equals("")){
                alert.setContentText("Please enter your username");
            }else if(passwordField.getText().equals("")){
                alert.setContentText("Please enter your password");
            }else if(confirmPasswordField.getText().equals("")){
                alert.setContentText("Please enter your confirm password");
            }else if(phoneNumberTextField.getText().equals("")){
                alert.setContentText("Please enter your phone number");
            }
            alert.showAndWait();
        }else {
            if(!passwordField.getText().equals(confirmPasswordField.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not create staff account");
                alert.setContentText("Not match password and confirm password");
                alert.showAndWait();
            }else {
                if(accountBank.findUserName(usernameTextField.getText()) || accountStaffList.findUserName(usernameTextField.getText()) ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("");
                    alert.setTitle("Can not create staff account");
                    alert.setContentText("This username is already used");
                    alert.showAndWait();
                }else{
                    String name = nameTextField.getText();
                    String surname = surnameTextField.getText();
                    String username = usernameTextField.getText();
                    String password = passwordField.getText();
                    String phoneNumber = phoneNumberTextField.getText();
                    AccountStaff accStaff = new AccountStaff(username, password, name, surname, phoneNumber, "staff");
                    if(!uploadTextField.getText().equals("")){
                        accStaff.setImage(imageTarget);
                    }
                    accountStaffList.addAccount(accStaff);
                    accountStaffDataSource.setAccountData(accountStaffList);

                    Button createAccountButton = (Button) event.getSource();
                    Stage stageAdminMenu = (Stage)createAccountButton.getScene().getWindow();

                    FXMLLoader loaderAdminMenu = new FXMLLoader(getClass().getResource("/admin_menu.fxml"));
                    stageAdminMenu.setScene(new Scene(loaderAdminMenu.load(), 800, 600));

                    AdminMenuController adminMenuController = loaderAdminMenu.getController();
                    adminMenuController.setAccountBank(accountBank);
                    stageAdminMenu.show();
                }

            }
        }

    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }


}
