package project.java_63.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


public class InstructionController {
    @FXML ComboBox<String> roleComboBox;
    @FXML Label roleLabel;
    @FXML Label infoLabel;

    @FXML public void initialize(){
        roleComboBox.getItems().add("Resident");
        roleComboBox.getItems().add("Staff");
        roleComboBox.getItems().add("Admin");
        roleComboBox.setValue("Resident");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
        roleLabel.setText("Resident");
        infoLabel.setText("1.สามารถเข้าสู่ระบบได้ที่ปุ่ม Resident\n" +
                "2.หลังจาก Login เข้ามาแล้วจะสามารถตรวจสอบ จดหมาย/เอกสาร หรือพัสดุได้ตามเลขห้องที่ตารางสิ่งของ\n" +
                "3.สามารถตรวจสอบตารางสิ่งของที่รับไปแล้วได้ที่ Recieved mail เมื่อกดเข้าไปแล้วจะพบกับตารางสิ่งของ\nที่ผู้พักอาศัยได้รับไปแล้ว\n" +
                "4.ผู้พักอาศัยสามารถสร้างบัญชีผู้ใช้ได้ที่ Register และต้องกรอกข้อมูลห้องพัก, ชื่อ-นามสกุลให้ถูกต้อง\nตามข้อมูลที่ทางเจ้าหน้าที่ส่วนกลางได้กรอกไว้ในระบบ\n" +
                "5.ผู้พักอาศัยสามารถเปลี่ยนรหัสผ่านได้ที่ Change password");
    }

    @FXML public void handleRoleComboBoxOnAction(){
        if(roleComboBox.getValue().equals("Resident")){
            roleLabel.setText("Resident");
            infoLabel.setText("1.สามารถเข้าสู่ระบบได้ที่ปุ่ม Resident\n" +
                    "2.หลังจาก Login เข้ามาแล้วจะสามารถตรวจสอบ จดหมาย/เอกสาร หรือพัสดุได้ตามเลขห้องที่ตารางสิ่งของ\n" +
                    "3.สามารถตรวจสอบตารางสิ่งของที่รับไปแล้วได้ที่ Received mail เมื่อกดเข้าไปแล้วจะพบกับตารางสิ่งของ\nที่ผู้พักอาศัยได้รับไปแล้ว\n" +
                    "4.ผู้พักอาศัยสามารถสร้างบัญชีผู้ใช้ได้ที่ Register และต้องกรอกข้อมูลห้องพัก, ชื่อ-นามสกุลให้ถูกต้อง\nตามข้อมูลที่ทางเจ้าหน้าที่ส่วนกลางได้กรอกไว้ในระบบ\n" +
                    "5.ผู้พักอาศัยสามารถเปลี่ยนรหัสผ่านได้ที่ Change password");
        }else if(roleComboBox.getValue().equals("Staff")){
            roleLabel.setText("Staff");
            infoLabel.setText("1.เจ้าหน้าที่ส่วนกลางสามารถ Login ได้ที่ปุ่ม Staff\n" +
                    "   1.1 ถ้าหากเจ้าหน้าที่ส่วนกลางโดนบล็อคจากระบบ โปรดติดต่อ ผู้ดูแลระบบให้ปลดบล็อค\n" +
                    "2.เจ้าหน้าที่ส่วนกลางสามารถเข้าไปจัดการเกี่ยวกับผู้พักอาศัยและห้องพักได้ที่ Resident management\n" +
                    "   2.1 เจ้าหน้าที่ส่วนกลาง สามารถเพิ่มห้องพักได้ที่ Add room และเพิ่มผู้เข้าพักอาศัยได้ที่ Add resident\n" +
                    "   2.2 เจ้าหน้าที่ส่วนกลางสามารถลบผู้พักอาศัยในห้องพักหรือเปลี่ยนข้อมูลผู้เข้าพักได้ที่ \nResident information\n" +
                    "3.เจ้าหน้าที่ส่วนกลางสามารถเข้าไปจัดการเกี่ยวกับจดหมาย/เอกสาร หรือพัสดุได้ที่ Mailbox management\n" +
                    "   3.1 ถ้าหากมีคนมารับพัสดุ เจ้าหน้าที่ส่วนกลางสามารถกดรับพัสดุได้โดยกดคลิกที่ หมายเลขห้องที่มารับ \nและเลือกชื่อผู้ที่มารับได้ที่ตัวเลือก Resident จากนั้นกด Receive เพื่อรับของ\n" +
                    "   3.2 เจ้าหน้าที่ส่วนกลางสามารถเพิ่มพัสดุเข้าในระบบได้โดยกดปุ่ม Add mail จากนั้นกรอกข้อมูลให้ถูกต้อง\n" +
                    "   3.3 สามารถเข้าดูจดหมาย/เอกสาร หรือพัสดุได้ที่ Received mail\n" +
                    "4.เจ้าหน้าที่ส่วนกลางสามารถเปลี่ยนรหัสผ่านได้ที่ Change password");
        }else if(roleComboBox.getValue().equals("Admin")){
            roleLabel.setText("Admin");
            infoLabel.setText("1.ผู้ดูแลระบบสามารถ Login ได้ที่ปุ่ม Admin\n" +
                    "2.ผู้ดูแลระบบสามารถสร้างบัญชีผู้ใช้ของเจ้าหน้าที่ส่วนกลางได้ที่ Create Staff Account\n" +
                    "3.ผู้ดูแลระบบสามารถจัดการบัญชีผู้ใช้ของเจ้าหน้าที่ส่วนกลางได้ที่ Manage Staff Account\n" +
                    "   3.1 ผู้ดูแลระบบสามารถระงับสิทธิการเข้าใช้ของเจ้าหน้าที่ส่วนกลางได้โดยคลิกที่ชื่อเจ้าหน้าที่ส่วนกลาง\nจากนั้นกด Deactivate\n" +
                    "       3.1.1 ถ้าหากเจ้าหน้าที่ส่วนกลางที่โดนระงับสิทธิพยายามจะเข้าสู่ระบบจะมีจำนวนครั้งบอกว่าพยายาม\nเข้ามาทั้งหมดกี่ครั้ง\n" +
                    "   3.2 ผู้ดูแลระบบสามารถยกเลิกการระงับสิทธิการเข้าใช้ได้โดย กดปุ่ม Activate\n" +
                    "4.ผู้ดูแลระบบสามารถเปลี่ยนรหัสผ่านได้ที่ Change password");
        }
    }


}
