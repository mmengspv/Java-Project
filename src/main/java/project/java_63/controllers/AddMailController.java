package project.java_63.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.java_63.condoinfo.Condo;
import project.java_63.models.*;
import project.java_63.service.MailboxFileDataSource;
import project.java_63.service.RoomDataSource;


import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddMailController {
    @FXML Button backButton;
    @FXML Button uploadButton;
    @FXML Button addMailButton;
    @FXML ImageView image;
    @FXML ComboBox<String> typeComboBox;
    @FXML ComboBox<String> importanceComboBox;
    @FXML TextField uploadTextField;
    @FXML TextField roomNumberTextField;
    @FXML TextField nameReceiverTextField;
    @FXML TextField surnameReceiverTextField;
    @FXML TextField nameSenderTextField;
    @FXML TextField surnameSenderTextField;
    @FXML TextField widthTextField;
    @FXML TextField heightTextField;
    @FXML TextField shippingCompanyTextField;
    @FXML TextField trackingNumberTextField;
    private Condo condoList;
    private RoomDataSource roomDataSource;
    private AccountBank accountBank;
    private MailboxFileDataSource mailboxFileDataSource;
    private Mailbox mailboxList;
    private File imageFile;
    private Path target;
    private String imageTarget;

    @FXML public void initialize(){
        mailboxFileDataSource = new MailboxFileDataSource("data", "mailbox.csv");
        mailboxList = mailboxFileDataSource.getMailboxList();

        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

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
                        File destDir = new File("data/images");
                        destDir.mkdirs();
                        // RENAME FILE
                        String[] fileSplit = imageFile.getName().split("\\.");
                        String filename = LocalDate.now()+"_"+System.currentTimeMillis()+"."+fileSplit[fileSplit.length - 1];
                        target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+filename);
                        imageTarget = "data" + "/" + "images" + "/" + filename;
                        // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                        Files.copy(imageFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                        // SET NEW FILE PATH TO IMAGE
                        uploadTextField.setText(imageFile.toPath().toString());
                        image.setImage(new Image(target.toUri().toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        typeComboBox.getItems().addAll("Letter", "Document", "Parcel");
        typeComboBox.setValue("Letter");
        importanceComboBox.setDisable(true);
        trackingNumberTextField.setDisable(true);
        shippingCompanyTextField.setDisable(true);
        importanceComboBox.getItems().addAll("enroll", "express", "secret");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
            }
        });

    }


    @FXML public void handleTypeComboBoxOnAction(){
        if(typeComboBox.getValue().equals("Letter")){
            importanceComboBox.setDisable(true);
            trackingNumberTextField.setDisable(true);
            shippingCompanyTextField.setDisable(true);
        } else if(typeComboBox.getValue().equals("Document")){
            shippingCompanyTextField.setDisable(true);
            trackingNumberTextField.setDisable(true);
            importanceComboBox.setDisable(false);
        }else if(typeComboBox.getValue().equals("Parcel")){
            trackingNumberTextField.setDisable(false);
            shippingCompanyTextField.setDisable(false);
            importanceComboBox.setDisable(true);
        }
    }

    @FXML public void handleAddMailButtonOnAction(ActionEvent event) throws IOException {
        if(condoList.checkRoom(roomNumberTextField.getText())) {
            if ((nameReceiverTextField.getText().equals("") || surnameReceiverTextField.getText().equals("") || roomNumberTextField.getText().equals("")
                    || nameSenderTextField.getText().equals("") || surnameSenderTextField.getText().equals("") || widthTextField.getText().equals("")
                    || heightTextField.getText().equals(""))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not add mail to this room");
                if (nameReceiverTextField.getText().equals("")) {
                    alert.setContentText("Please enter name receiver");
                } else if (surnameReceiverTextField.getText().equals("")) {
                    alert.setContentText("Please enter surname receiver");
                } else if (roomNumberTextField.getText().equals("")) {
                    alert.setContentText("Please enter room number");
                } else if (nameSenderTextField.getText().equals("")) {
                    alert.setContentText("Please enter name sender");
                } else if (surnameSenderTextField.getText().equals("")) {
                    alert.setContentText("Please enter surname sender");
                } else if (widthTextField.getText().equals("")) {
                    alert.setContentText("Please enter width");
                } else if (heightTextField.getText().equals("")) {
                    alert.setContentText("Please enter height");
                }
                alert.showAndWait();
            } else if ((importanceComboBox.getValue() == null) && typeComboBox.getValue().equals("Document")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not add mail to this room");
                alert.setContentText("Please select level of importance");
                alert.showAndWait();
            } else if ((trackingNumberTextField.getText().equals("") || shippingCompanyTextField.getText().equals("")) && typeComboBox.getValue().equals("Parcel")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("");
                alert.setTitle("Can not add mail to this room");
                if (shippingCompanyTextField.getText().equals("")) {
                    alert.setContentText("Please enter shipping company name");
                } else if (trackingNumberTextField.getText().equals("")) {
                    alert.setContentText("Please enter tracking number of this mail");
                }
                alert.showAndWait();
            } else {
                LocalDateTime date = LocalDateTime.now();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatTime = date.format(dateFormat);
                if(typeComboBox.getValue().equals("Letter")){
                    Letter letter = new Letter(typeComboBox.getValue(), nameReceiverTextField.getText(), surnameReceiverTextField.getText(),
                            roomNumberTextField.getText(), nameSenderTextField.getText(), surnameSenderTextField.getText(), widthTextField.getText(),
                            heightTextField.getText());
                    if(!uploadTextField.getText().equals("")){
                        letter.setImage(imageTarget);
                    }
                    letter.setStaffReceiver(accountBank.getCurrentAccount().getName());
                    letter.setDateIn(formatTime);
                    mailboxList.addSuppliesToLocker(letter);
                }else if(typeComboBox.getValue().equals("Document")){
                    Document document = new Document(typeComboBox.getValue(), nameReceiverTextField.getText(), surnameReceiverTextField.getText(),
                                        roomNumberTextField.getText(), nameSenderTextField.getText(), surnameSenderTextField.getText(),
                                        widthTextField.getText(), heightTextField.getText(), importanceComboBox.getValue());
                    if(!uploadTextField.getText().equals("")){
                        document.setImage(imageTarget);
                    }
                    document.setDateIn(formatTime);
                    document.setStaffReceiver(accountBank.getCurrentAccount().getName());
                    mailboxList.addSuppliesToLocker(document);
                }else if(typeComboBox.getValue().equals("Parcel")){
                    Parcel parcel = new Parcel(typeComboBox.getValue(), nameReceiverTextField.getText(), surnameReceiverTextField.getText(),
                                    roomNumberTextField.getText(), nameSenderTextField.getText(), surnameSenderTextField.getText(),
                                    widthTextField.getText(), heightTextField.getText(), shippingCompanyTextField.getText(), trackingNumberTextField.getText());
                    if(!uploadTextField.getText().equals("")){
                        parcel.setImage(imageTarget);
                    }
                    parcel.setStaffReceiver(accountBank.getCurrentAccount().getName());
                    parcel.setDateIn(formatTime);
                    mailboxList.addSuppliesToLocker(parcel);
                }

                mailboxFileDataSource.setMailboxListData(mailboxList);

                Button addMailButton = (Button) event.getSource();
                Stage stageStaffMailboxManagement = (Stage)addMailButton.getScene().getWindow();

                FXMLLoader loaderStageStaffMail = new FXMLLoader(getClass().getResource("/staff_mailbox_management.fxml"));
                stageStaffMailboxManagement.setScene(new Scene(loaderStageStaffMail.load(), 800, 600));

                StaffMailboxManagementController staffMailboxManagement = loaderStageStaffMail.getController();
                staffMailboxManagement.setAccountBank(accountBank);
                stageStaffMailboxManagement.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not add mail to this room");
            alert.setContentText("Don't have this room number in condo.\nPlease check room number");
            alert.showAndWait();
        }
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageStaffMailboxManagement = (Stage)backButton.getScene().getWindow();

        FXMLLoader loaderStaffMailboxManagement = new FXMLLoader(getClass().getResource("/staff_mailbox_management.fxml"));
        stageStaffMailboxManagement.setScene(new Scene(loaderStaffMailboxManagement.load(), 800, 600));

        StaffMailboxManagementController staffMailboxManagementController = loaderStaffMailboxManagement.getController();
        staffMailboxManagementController.setAccountBank(accountBank);
        stageStaffMailboxManagement.show();
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
