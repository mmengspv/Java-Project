package project.java_63.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project.java_63.condoinfo.Condo;
import project.java_63.models.*;
import project.java_63.service.MailboxFileDataSource;
import project.java_63.service.RoomDataSource;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class StaffMailboxManagementController {
    @FXML Button backButton;
    @FXML ImageView image;
    @FXML Button addMailButton;
    @FXML Button receivedMailButton;
    @FXML Button receiveButton;
    @FXML TableView<Letter> mailboxTableView;
    @FXML TableColumn<Letter, String> dateTableColumn;
    @FXML TableColumn<Letter, String> receiverTableColumn;
    @FXML TableColumn<Letter, String> roomTableColumn;
    @FXML TableColumn<Letter, String> statusTableColumn;
    @FXML TableColumn<Letter, String> typeTableColumn;
    @FXML TextField roomNumberSearchTextField;
    @FXML ComboBox<String> residentReceiveComboBox;
    @FXML ComboBox<String> sortComboBox;
    @FXML Label roomLabel;
    @FXML Label receiverLabel;
    @FXML Label senderLabel;
    @FXML Label typeLabel;
    @FXML Label widthLabel;
    @FXML Label heightLabel;
    @FXML Label importanceLabel;
    @FXML Label shippingByLabel;
    @FXML Label trackingNumberLabel;
    @FXML Label staffLabel;
    @FXML Label staffReceivedTimeLabel;
    private Letter selectedMail;
    private AccountBank accountBank;
    private MailboxFileDataSource mailboxFileDataSource;
    private Mailbox mailboxList;
    private ObservableList<Letter> letterObservableList;
    private Condo condoList;
    private RoomDataSource roomDataSource;
    private Mailbox mailboxNew;

    @FXML public void initialize(){
        mailboxFileDataSource = new MailboxFileDataSource("data", "mailbox.csv");
        mailboxList = mailboxFileDataSource.getMailboxList();

        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
                dateTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateIn()));
                receiverTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().toStringReceiver()));
                roomTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
                typeTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
                statusTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus()));

                getMail();
                sortComboBox.getItems().addAll("Sort by date", "Sort by room");



                mailboxTableView.setItems(letterObservableList);
                mailboxTableView.setItems(search());
                mailboxTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        showSelectedMail(newValue);
                    }
                });
            }
        });
    }

    private void getMail(){
        mailboxNew = new Mailbox();
        for (Letter l: mailboxList.getMailboxList()){
            if(l.getStatus().equals("Not received")){
                mailboxNew.addSuppliesToLocker(l);
            }
        }
        ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
        Collections.sort(tmpList, (o1, o2) ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                Date o1DateIn = simpleDateFormat.parse(o1.getDateIn());
                Date o2DateIn = simpleDateFormat.parse(o2.getDateIn());
                if (o1DateIn.getTime() >= o2DateIn.getTime()) return -1;
                return 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        });
        letterObservableList = FXCollections.observableList(tmpList);
    }

    private void showSelectedMail(Letter letter){
        clearSelectedData();
        selectedMail = letter;
        roomLabel.setText(letter.getRoomNumber());
        receiverLabel.setText(letter.toStringReceiver());
        senderLabel.setText(letter.toStringSender());
        typeLabel.setText(letter.getType());
        widthLabel.setText(letter.getWidth());
        heightLabel.setText(letter.getHeight());
        File file = new File(letter.getImage());
        Image setImageToImageView = new Image(file.toURI().toString());
        image.setImage(setImageToImageView);
        if(letter.getType().equals("Document")){
            Document document = (Document)letter;
            importanceLabel.setText(document.getImportance());
        }else if(letter.getType().equals("Parcel")){
            Parcel parcel = (Parcel)letter;
            shippingByLabel.setText(parcel.getDelivery());
            trackingNumberLabel.setText(parcel.getTrackingNumber());
        }
        staffLabel.setText(letter.getStaffReceiver());
        staffReceivedTimeLabel.setText(letter.getDateIn());

        residentReceiveComboBox.getItems().clear();
        if(condoList.checkRoom(letter.getRoomNumber())){
            for(Person p: condoList.getCurrentRoom().getPersons()){
                residentReceiveComboBox.getItems().add(p.toString());
            }
        }
    }

    private void clearSelectedData(){
        roomLabel.setText("-");
        receiverLabel.setText("-");
        senderLabel.setText("-");
        typeLabel.setText("-");
        widthLabel.setText("-");
        heightLabel.setText("-");
        importanceLabel.setText("-");
        shippingByLabel.setText("-");
        trackingNumberLabel.setText("-");
        staffLabel.setText("-");
        staffReceivedTimeLabel.setText("-");
    }

    public SortedList<Letter> search() {
        FilteredList<Letter> filteredList = new FilteredList<>(letterObservableList, p -> true);
        roomNumberSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(room -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (room.getRoomNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Letter> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(mailboxTableView.comparatorProperty());
        return sortedList;
    }

    @FXML public void handleSortComboBoxOnAction(ActionEvent event){
        if(sortComboBox.getValue().equals("Sort by date")){
            mailboxTableView.getSelectionModel().clearSelection();
            letterObservableList.clear();

            ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
            Collections.sort(tmpList, (o1, o2) ->{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    Date o1DateIn = simpleDateFormat.parse(o1.getDateIn());
                    Date o2DateIn = simpleDateFormat.parse(o2.getDateIn());
                    if (o1DateIn.getTime() >= o2DateIn.getTime()) return -1;
                    return 1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            });
            letterObservableList = FXCollections.observableArrayList(tmpList);
            mailboxTableView.setItems(letterObservableList);
            mailboxTableView.setItems(search());

        }else if(sortComboBox.getValue().equals("Sort by room")){
            ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
            Collections.sort(tmpList, (o1, o2) ->{
                if (Integer.parseInt(o1.getRoomNumber()) >= Integer.parseInt(o2.getRoomNumber())) return 1;
                return -1;
            });
            letterObservableList = FXCollections.observableList(tmpList);
            mailboxTableView.setItems(letterObservableList);
            mailboxTableView.setItems(search());

        }
    }




    @FXML public void handleReceiveButtonOnAction(ActionEvent event) throws IOException {
        if(residentReceiveComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("");
            alert.setTitle("Can not Receive");
            alert.setContentText("Please select the name of the resident");
            alert.showAndWait();
        }else{
            selectedMail.setPickupBy(residentReceiveComboBox.getValue());
            selectedMail.setStatus("Received");
            selectedMail.setStaffOut(accountBank.getCurrentAccount().getName());
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatTime = date.format(dateFormat);
            selectedMail.setDateOut(formatTime);
            mailboxTableView.getSelectionModel().clearSelection();
            mailboxTableView.refresh();
            mailboxFileDataSource.setMailboxListData(mailboxList);

            Button receiveButton = (Button) event.getSource();
            Stage stageStaffMailboxManagement = (Stage)receiveButton.getScene().getWindow();

            FXMLLoader loaderStaffMailboxManagement = new FXMLLoader(getClass().getResource("/staff_mailbox_management.fxml"));
            stageStaffMailboxManagement.setScene(new Scene(loaderStaffMailboxManagement.load(), 800, 600));

            StaffMailboxManagementController staffMailboxManagementController = loaderStaffMailboxManagement.getController();
            staffMailboxManagementController.setAccountBank(accountBank);
            stageStaffMailboxManagement.show();
        }
    }


    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        Button backButton = (Button) event.getSource();
        Stage stageStaffMenu = (Stage)backButton.getScene().getWindow();

        FXMLLoader loaderStageStaffMenu = new FXMLLoader(getClass().getResource("/staff_menu.fxml"));
        stageStaffMenu.setScene(new Scene(loaderStageStaffMenu.load(), 800, 600));

        StaffMenuController staffMenuController = loaderStageStaffMenu.getController();
        staffMenuController.setAccountBank(accountBank);
        stageStaffMenu.show();
    }

    @FXML public void handleAddMailButtonOnAction(ActionEvent event) throws IOException {
        Button addMailButton = (Button) event.getSource();
        Stage stageAddMail = (Stage)addMailButton.getScene().getWindow();

        FXMLLoader loaderStageAddMail = new FXMLLoader(getClass().getResource("/add_mail.fxml"));
        stageAddMail.setScene(new Scene(loaderStageAddMail.load(), 800, 600));

        AddMailController addMailController = loaderStageAddMail.getController();
        addMailController.setAccountBank(accountBank);
        stageAddMail.show();
    }

    @FXML public void handleReceivedMailButtonOnAction(ActionEvent event) throws IOException {
        Button receivedMailButton = (Button) event.getSource();
        Stage stageReceivedMail = (Stage)receivedMailButton.getScene().getWindow();

        FXMLLoader loaderReceivedMail = new FXMLLoader(getClass().getResource("/received_mail.fxml"));
        stageReceivedMail.setScene(new Scene(loaderReceivedMail.load(), 800, 600));

        ReceivedMailController receivedMailController = loaderReceivedMail.getController();
        receivedMailController.setAccountBank(accountBank);
        stageReceivedMail.show();
    }



    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
