package project.java_63.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

public class MenuController {
    @FXML private Button residentButton;
    @FXML private Button staffButton;
    @FXML private Button adminButton;
    @FXML private Button stepToUseButton;
    @FXML private Button aboutMeButton;
//    private AccountBank accountBank;
//    private AccountHardcodeDataSource accHardCode;

    @FXML public void handleResidentButtonOnAction(ActionEvent event) throws IOException {
        Button residentButton = (Button)event.getSource();
        Stage stageResidentLogin = (Stage) residentButton.getScene().getWindow();

        FXMLLoader loaderResidentLogin = new FXMLLoader(getClass().getResource("/resident_login.fxml"));
        stageResidentLogin.setScene(new Scene(loaderResidentLogin.load(), 800, 600));

//        ResidentLogin rl = loaderResidentLogin.getController();
//        rl.setAccountBank(accountBank);
        stageResidentLogin.show();
    }

    @FXML public void handleAdminButtonOnAction(ActionEvent event) throws IOException {
        Button adminButton = (Button) event.getSource();
        Stage stageAdminLogin = (Stage) adminButton.getScene().getWindow();

        FXMLLoader loaderAdminLogin = new FXMLLoader(getClass().getResource("/admin_login.fxml"));
        stageAdminLogin.setScene(new Scene(loaderAdminLogin.load(),800,600));

        stageAdminLogin.show();
    }

    @FXML public void handleStaffButtonOnAction(ActionEvent event) throws IOException {
        Button staffButton = (Button) event.getSource();
        Stage stageStaffLogin = (Stage) staffButton.getScene().getWindow();

        FXMLLoader loaderStaffLogin = new FXMLLoader(getClass().getResource("/staff_login.fxml"));
        stageStaffLogin.setScene(new Scene(loaderStaffLogin.load(), 800, 600));

        stageStaffLogin.show();
    }

    @FXML public void handleStepToUseButtonOnAction(ActionEvent event) throws IOException {

            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/instruction.fxml"));
            newStage.setTitle("Instruction of the program");
            newStage.setScene(new Scene(root, 750, 600));

            newStage.setResizable(false);

            newStage.show();
//        }catch (Exception e){
//            System.err.println("Can not open instruction of the program");
//        }
    }

    @FXML public void handleAboutMeButtonOnAction(ActionEvent event) {
        try {
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/about_me.fxml"));
            newStage.setTitle("Profile");
            newStage.setScene(new Scene(root, 600, 600));

            newStage.setResizable(false);

            newStage.show();
        }catch (Exception e){
            System.err.println("Can not open profile of the program");
        }
    }

}
