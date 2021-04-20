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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ReceivedMailController {
    @FXML Button backButton;
    @FXML ImageView image;
    @FXML TableView<Letter> receivedMailTableView;
    @FXML TableColumn<Letter, String> dateTableColumn;
    @FXML TableColumn<Letter, String> receiverTableColumn;
    @FXML TableColumn<Letter, String> typeTableColumn;
    @FXML TableColumn<Letter, String> roomTableColumn;
    @FXML TableColumn<Letter, String> statusTableColumn;
    @FXML TextField roomNumberSearchTextField;
    @FXML Label roomLabel;
    @FXML Label receiverLabel;
    @FXML Label senderLabel;
    @FXML Label receiveByLabel;
    @FXML Label typeLabel;
    @FXML Label widthLabel;
    @FXML Label heightLabel;
    @FXML Label importanceLabel;
    @FXML Label shippingByLabel;
    @FXML Label trackingNumberLabel;
    @FXML Label staffLabel;
    @FXML Label staffReceivedTimeLabel;
    @FXML Label residentReceivedTimeLabel;
    @FXML Label staffOutLabel;
    @FXML ComboBox<String> sortComboBox;
    private Mailbox mailboxNew;
    private AccountBank accountBank;
    private Letter selectMail;
    private MailboxFileDataSource mailboxFileDataSource;
    private Mailbox mailboxList;
    private ObservableList<Letter> letterObservableList;
    private Condo condoList;
    private RoomDataSource roomDataSource;

    @FXML public void initialize(){
        mailboxFileDataSource = new MailboxFileDataSource("data", "mailbox.csv");
        mailboxList = mailboxFileDataSource.getMailboxList();

        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
                dateTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateOut()));
                typeTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
                receiverTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNameReceiver()));
                roomTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
                statusTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus()));
                sortComboBox.getItems().addAll("Sort by date", "Sort by room");
                getMail();
                receivedMailTableView.setItems(letterObservableList);
                receivedMailTableView.setItems(search());


                receivedMailTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectedMail(newValue);
                    }
                });
            }
        });
    }

    private void getMail(){
        mailboxNew = new Mailbox();
        if(accountBank.getCurrentAccount().getRole().equals("staff")) {
            for (Letter l : mailboxList.getMailboxList()) {
                if (l.getStatus().equals("Received")) {
                    mailboxNew.addSuppliesToLocker(l);
                }
            }
        }else if(accountBank.getCurrentAccount().getRole().equals("resident")){
            for (Letter l: mailboxList.getMailboxList()){
                if(condoList.checkRoomByName(accountBank.getCurrentAccount().getName(), accountBank.getCurrentAccount().getSurname())) {
                    if (l.getStatus().equals("Received") && condoList.getCurrentRoomUser().getRoomNumber().equals(l.getRoomNumber())) {
                        mailboxNew.addSuppliesToLocker(l);

                    }
                }
            }
        }
        ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
        Collections.sort(tmpList, (o1, o2) ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                Date o1DateOut = simpleDateFormat.parse(o1.getDateOut());
                Date o2DateOut = simpleDateFormat.parse(o2.getDateOut());
                if (o1DateOut.getTime() >= o2DateOut.getTime()) return -1;
                return 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return -1;
        });
        letterObservableList = FXCollections.observableList(tmpList);
    }

    private void selectedMail(Letter letter){
        clearSelectedData();
        selectMail = letter;
        roomLabel.setText(letter.getRoomNumber());
        receiverLabel.setText(letter.toStringReceiver());
        senderLabel.setText(letter.toStringSender());
        typeLabel.setText(letter.getType());
        widthLabel.setText(letter.getWidth());
        heightLabel.setText(letter.getHeight());
        staffLabel.setText(letter.getStaffReceiver());
        staffReceivedTimeLabel.setText(letter.getDateIn());
        receiveByLabel.setText(letter.getPickupBy());
        residentReceivedTimeLabel.setText(letter.getDateOut());
        staffOutLabel.setText(letter.getStaffOut());
        File file = new File(letter.getImage());
        Image newImage = new Image(file.toURI().toString());
        image.setImage(newImage);
        if(letter.getType().equals("Document")){
            Document document = (Document)letter;
            importanceLabel.setText(document.getImportance());
        }else if(letter.getType().equals("Parcel")){
            Parcel parcel = (Parcel)letter;
            shippingByLabel.setText(parcel.getDelivery());
            trackingNumberLabel.setText(parcel.getTrackingNumber());
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
        receiveByLabel.setText("-");
        residentReceivedTimeLabel.setText("-");
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
        sortedList.comparatorProperty().bind(receivedMailTableView.comparatorProperty());
        return sortedList;
    }

    @FXML public void handleSortComboBoxOnAction(ActionEvent event){
        if(sortComboBox.getValue().equals("Sort by date")){
//            receivedMailTableView.getSelectionModel().clearSelection();
//            letterObservableList.clear();
            receivedMailTableView.refresh();
            ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
            Collections.sort(tmpList, (o1, o2) ->{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    Date o1DateOut = simpleDateFormat.parse(o1.getDateOut());
                    Date o2DateOut = simpleDateFormat.parse(o2.getDateOut());
                    if (o1DateOut.getTime() >= o2DateOut.getTime()) return -1;
                    return 1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            });
            letterObservableList = FXCollections.observableArrayList(tmpList);
            receivedMailTableView.setItems(letterObservableList);
            receivedMailTableView.setItems(search());

        }else if(sortComboBox.getValue().equals("Sort by room")){
            ArrayList<Letter> tmpList = (ArrayList<Letter>)mailboxNew.getMailboxList().clone();
            receivedMailTableView.refresh();
            Collections.sort(tmpList, (o1, o2) ->{
                if (Integer.parseInt(o1.getRoomNumber()) >= Integer.parseInt(o2.getRoomNumber())) return 1;
                return -1;
            });
            letterObservableList = FXCollections.observableList(tmpList);
            receivedMailTableView.setItems(letterObservableList);
            receivedMailTableView.setItems(search());

        }
    }

    @FXML public void handleBackButtonOnAction(ActionEvent event) throws IOException {
        if(accountBank.getCurrentAccount().getRole().equals("staff")) {
            Button backButton = (Button) event.getSource();
            Stage stageStaffMailboxManagement = (Stage) backButton.getScene().getWindow();

            FXMLLoader loaderStaffMailboxManagement = new FXMLLoader(getClass().getResource("/staff_mailbox_management.fxml"));
            stageStaffMailboxManagement.setScene(new Scene(loaderStaffMailboxManagement.load(), 800, 600));

            StaffMailboxManagementController staffMailboxManagementController = loaderStaffMailboxManagement.getController();
            staffMailboxManagementController.setAccountBank(accountBank);
            stageStaffMailboxManagement.show();
        }else if(accountBank.getCurrentAccount().getRole().equals("resident")){
            Button backButton = (Button) event.getSource();
            Stage stageResidentMenu = (Stage) backButton.getScene().getWindow();

            FXMLLoader loaderResidentMenu = new FXMLLoader(getClass().getResource("/resident_menu.fxml"));
            stageResidentMenu.setScene(new Scene(loaderResidentMenu.load(), 800, 600));

            ResidentMenuController residentMenuController = loaderResidentMenu.getController();
            residentMenuController.setAccountBank(accountBank);
            stageResidentMenu.show();
        }
    }


    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }
}
