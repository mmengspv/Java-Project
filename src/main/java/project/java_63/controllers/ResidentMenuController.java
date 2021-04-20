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

public class ResidentMenuController {
    @FXML Button logoutButton;
    @FXML Button changePasswordButton;
    @FXML ImageView image;
    @FXML TableView<Letter> mailboxTableView;
    @FXML TableColumn<Letter, String> dateTableColumn;
    @FXML TableColumn<Letter, String> receiverTableColumn;
    @FXML TableColumn<Letter, String> roomTableColumn;
    @FXML TableColumn<Letter, String> statusTableColumn;
    @FXML TableColumn<Letter, String> typeTableColumn;
    @FXML TextField roomNumberSearchTextField;
    @FXML ComboBox<String> residentReceiveComboBox;
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
    @FXML ComboBox<String> sortComboBox;

    private ObservableList<Letter> letterObservableList;
    private Letter selectedMail;
    private RoomDataSource roomDataSource;
    private Condo condoList;
    private MailboxFileDataSource mailboxFileDataSource;
    private Mailbox mailboxList;
    private AccountBank accountBank;
    private Mailbox mailboxNew;

    public void initialize(){
        roomDataSource = new RoomDataSource("data", "room.csv");
        condoList = roomDataSource.getCondoListData();

        mailboxFileDataSource = new MailboxFileDataSource("data", "mailbox.csv");
        mailboxList = mailboxFileDataSource.getMailboxList();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(accountBank.getCurrentAccount().getUsername());
                dateTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateIn()));
                roomTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRoomNumber()));
                receiverTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().toStringReceiver()));
                typeTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
                statusTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus()));
                sortComboBox.getItems().addAll("Sort by date", "Sort by room");

                getMail();

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
            if(condoList.checkRoomByName(accountBank.getCurrentAccount().getName(), accountBank.getCurrentAccount().getSurname())) {
                if (l.getStatus().equals("Not received") && condoList.getCurrentRoomUser().getRoomNumber().equals(l.getRoomNumber())) {
                    mailboxNew.addSuppliesToLocker(l);

                }
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
        staffLabel.setText(letter.getStaffReceiver());
        staffReceivedTimeLabel.setText(letter.getDateIn());
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
//            mailboxTableView.refresh();
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

    @FXML public void handleReceivedMailButtonOnAction(ActionEvent event) throws IOException {
        Button receivedMailButton = (Button) event.getSource();
        Stage stageReceivedMail = (Stage)receivedMailButton.getScene().getWindow();

        FXMLLoader loaderReceivedMail = new FXMLLoader(getClass().getResource("/received_mail.fxml"));
        stageReceivedMail.setScene(new Scene(loaderReceivedMail.load(), 800, 600));

        ReceivedMailController receivedMailController = loaderReceivedMail.getController();
        receivedMailController.setAccountBank(accountBank);
        stageReceivedMail.show();
    }

    @FXML public void handleChangePasswordButtonOnAction(ActionEvent event) throws IOException {
        Button changePasswordButton = (Button)event.getSource();
        Stage stageChangePassword = (Stage) changePasswordButton.getScene().getWindow();
        FXMLLoader loaderChangePassword = new FXMLLoader(getClass().getResource("/change_password.fxml"));
        stageChangePassword.setScene(new Scene(loaderChangePassword.load(),800, 600));

        ChangePasswordController changePass = loaderChangePassword.getController();
        changePass.setAccountBank(accountBank);
        stageChangePassword.show();
    }

    @FXML public void handleLogoutButtonOnAction(ActionEvent event) throws IOException {
        Button logoutButton = (Button) event.getSource();
        Stage stageMain = (Stage) logoutButton.getScene().getWindow();

        FXMLLoader loaderChangePassword = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        stageMain.setScene(new Scene(loaderChangePassword.load(), 800, 600));

        stageMain.show();
    }

    public void setAccountBank(AccountBank accountBank){
        this.accountBank = accountBank;
    }



}
