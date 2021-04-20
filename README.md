# โครงงานวิชา 01418211 Software Constructiion ภาคต้น ปีการศึกษา 2563
---
                     
โปรแกรมบันทึกการรับ/ส่ง จดหมาย เอกสาร หรือพัสดุ สำหรับเจ้าหน้าที่ส่วนกลางของคอนโด (Condo Mailing Record)
                     
##### สิ่งที่พัฒนาในแต่ละครั้ง

-   ครั้งที่ 1  -- วันที่ 21 กันยายน พ.ศ. 2563
    - เป็นการ add maven to project 
    - สร้าง class เพื่อเก็บข้อมูลของบัญชีผู้ใช้ต่างๆ ได้แก่
        * Account.java
        * AccountBank.java
    - สร้าง Controllers เพื่อใช้ในการทำงานคำสั่ง ได้แก่
        * AdminLogin.java 
        * Controllers.java
        * ResidentLogin.java
        * StaffLogin.java
        * StepToUse.java
        * AboutMe.java
    - สร้าง GUI ในการทำงาน ไฟล์ . fxml ได้แก่
        * admin_login.fxml
        * resident _login
        * staff_login
        * welcome.fxml
    
- ครั้งที่ 2 --วันที่ 22 กันยายน พ.ศ. 2563
    - แก้ไข class ในการทำงานต่างๆ เพิ่ม method ที่จำเป็น เข้าไปใน class ต่างๆ
    - มีการสร้าง Hardcode มาทดสอบการทำงานเล็กน้อย
    - เพิ่ม Controllers ที่มาทำงานร่วมกับ GUI เพิ่มขึ้น ได้แก่
        * ResidentLogin.java
        * ResidentMenu.java
    - เพิ่ม GUI ในการแสดงผล ได้แก่
        * resident_login.fxml
        * resident_menu.fxml

- ครั้งที่ 3 -- วันที่ 29 กันยายน พ.ศ. 2563
    - แก้ไข Class Account.java
    - มีการใช้ polymorhpisum
    - มีการเพิ่ม Class Condo.java และ Room.java เพื่อนำมาใช้ในการสร้างห้อง เพิ่มผู้พักอาศัยเข้าสู่ห้องพัก
    - เพิ่ม Class ในการทำงานของ พัสดุ ได้แก่
        * Letter.java
        * Locker.java
        * Parcel.java
        * Document.java
    - มีการเพิ่ม Class การสร้างคน เพื่อมาใช้ในการรับ/ส่งพัสดุ ได้แก่
        * Person.java
        * PersonList.java

- ครั้งที่ 4 -- วันที่ 6 ตุลาคม พ.ศ. 2563
    - มีการสร้างไฟล์ csv มาเพื่ออ่านเขียนข้อมูล
    - มีการแก้ไข method และข้อมูลใน class ResidentLogin.java กับ ResidentMenu.java
    - เพิ่ม method ที่จำเป็นใน class Account, AccountBank
    - มีการสร้าง Class เพื่อนำมาอ่านเขียนข้อมูลโดยเฉพาะ ได้แก่
        * AccountDataSource
        * LockerDataSource

- ครั้งที่ 5 -- วันที่ 14 ตุลาคม พ.ศ. 2563
    - มีการสร้าง ไฟล์ csv เพื่อนำมาเก็บข้อมูลในโปรแกรมมากขึ้น ได้แก่
        * account.csv
        * staff_account.csv
        * admin_account.csv
    - มีการสร้าง Class models มากขึ้น
    - มีการสร้างที่อ่านเขียนข้อมูลเพิ่มขึ้น ได้แก่
        * AccountStaffDataSource
        * LockerDataSource
    -  มีการเพิ่ม Controllers มากขึ้นเพื่อนำมาควบคุม การทำงานของ GUI ได้แก่ 
    -  สามารถ สร้าง Account ของ staff และ admin ได้
    -สามารถเปลี่ยนแปลงรหัสผ่านได้
    - เพิ่ม GUI ในการแสดงผลเพิ่มมากขึ้น

- ครั้งที่ 6 -- วันที่ 20 ตุลาคม พ.ศ. 2563
    - มีการเพิ่มข้อมูลในไฟล์ csv แก้ไข ไฟล์ csv
    - มีการเพิ่มเติม class ต่างๆเพื่อใช้ในการเก็บข้อมูลของบัญชี้ผู้ใช้
    - มีการเรียกใช้ Tableview TableColumn, ComboBox และอื่นๆ เพื่อทำให้ข้อมูลโชว์ขึ้นมูลและเรียงให้ตู้ต้อง
    - มีการทำให้ลูกบ้านมีการ register สมัครเข้าใช้โปรแกรม
    - มีการ sort ข้อมูล 
    - มีการเข้าสู้ระบบได้อย่างถูกต้องครบถ้ว- มีการเพิ่มข้อมูลพัสดุเข้าสู่ระบบ สามารถดูข้อมูลได้
    - มีการเพิ่มห้องพัดอาศัยเข้าไปในคอนโด และมีการ เพิ่มผู้พักอาศัยเข้าไปในห้อง
    - สร้างการทำงาน ของ user
    - เขียน Readme.md
    - มีการสร้าง directory jarfile เพิ้มขึ้น
    
- ครั้งที่ 7 -- วันที่ 27 ตุลาคม พ.ศ. 2563
    - มีการเพิ่มการออกแบบ และตกแต่งหน้า User interface
    - เพิ่มหน้า profile about me
    - เพิ่มคำแนะนำของโปรแกรม Instruction
    - เพิ่มข้อมูลเข้าในโปรแกรม
    - แก้บัค error ต่างๆ
- ครั้งที่ 8 -- วันที่ 30 ตุลาคม พ.ศ. 2563
    - แก้บัค error
    - แก้ไขคำผิด 


### โครงสร้างไฟล์

- data เอาไว้เก็บไฟล์ .csv เอาไว้สำหรับการอ่านเขียนไฟล์ และ โฟลเดอร์ในการเก็บรูปภาพ ดังนี้
    * โฟลเดอร์ image เอาไว้เก็บรูปภาพของจดหมาย เอกสาร หรือพัสดุที่เข้ามาในระบบ
    * โฟลเดอร์ staff_images เอาไว้สำหรับเก็บรูปภาพเจ้าหน้าที่ส่วนกลาง ของระบบ
    * account.csv เป็นไฟล์เอาไว้สำหรับ เก็บข้อมูลบัญชีผู้ใช้ของผู้พักอาศัยทั้งหมด
    * admin_account.csv เป็นไฟล์เอาไว้สำหรับ เก็บข้อมูลบัญชีผู้ดูแลระบบทั้งหมด
    * staff_account.csv เป็นไฟล์ที่เอาไว้สำหรับเก็บข้อมูลบัญชีเจ้าหน้าที่ส่วนกลางทั้งหมด
    * mailbox.csv เป็นไฟล์ที่เอาไว้สำหรับเก็บข้อมูลจดหมาย เอกสาร หรือพัสดุที่ถูกรับเข้ามาในระบบ
    * room.csv เป็นไฟล์ที่เอาไว้เก็บข้อมูลห้องพัก และข้อมูลผู้พักอาศัยในห้องพักนั้นๆ
- condo mailing record เป็นโฟลเดอร์ที่เก็บ jarfile และข้อมูลต่างๆเพื่อนำมาอ่าน/เขียนข้อมูล
    * มัโฟลเดอร์ data เอาไว้เก็บไฟล์ .csv เอาไว้สำหรับการอ่านเขียนไฟล์ และ โฟลเดอร์ในการเก็บรูปภาพ
    * มี lib เก็บ bootstrap dependency ไว้เพื่อตกแต่งโปรแกรม
    * มี jar file 6210406734.jar เป็นโปรแกรม jarfile เพื่อเอาไว้รับ/ส่งจดหมาย
    * มี ไฟล์ PDF 6210406734.pdf เป็นคู่มือการใช้งานของโปรแกรม
    * มี UML Diagram 6210406734-UML.png ของ Models
- project.java_63 เอาไว้สำหรับการใส่ class ต่างๆของโปรแกรม รวมถึง class Main ด้วย
- condoinfo เป็น directory ที่เอาไว้เก็บ Class Room.java และ Condo.java
- controllers เป็น directory ที่เอาไว้เก็บคำสั่งการทำงานควบคู่ไปกับ scene builder ได้แก่
    * AboutMeController
    * AddMailController
    * AddResidentToRoomController
    * AddRoomController
    * AdminLoginController
    * AdminManageStaffController
    * AdminMenuController
    * ChangePasswordController
    * CreateStaffAccountController
    * InstructionController
    * MenuController
    * ReceivedMailController
    * ResidentInformationController
    * ResidentLoginController
    * ResidentManagementController
    * ResidentMenuController
    * ResidentRegisterController
    * StaffLoginController
    * StaffMailboxManagementController
    * StaffMenuController
- models เป็น directory ที่เอาไว้เก็บ class ต้นแบบของโปรแกรม การสร้าง object
    * Account
    * AccountBank
    * AccountStaff
    * Document
    * Letter
    * Mailbox
    * Parcel
    * Person

- service เป็น directory ที่เอาไว้เก็บ Class ที่เอาไว้อ่านเขียนโปรแกรม ในการเรียกใช้ไฟล์ csv มาใช้
    * AccountDataSource
    * AccountStaffDataSource
    * MailboxFileDataSource
    * RoomDataSource
    
- resources
    * เก็บไฟล์ FXML หรือ .fxml ในการแสดงผลหน้าจอของโปรแกรม
    * เก็บไฟล์ css เอาไว้ใช้ตกแต่ง Graphic User Interface
    
### วิธีการติดตั้งโปรแกรม
-  วิธีที่ 1 Double click ที่ไฟล์ 6210406734.jar 

- วิธีที่ 2 กรณีที่ไม่สามารถ Double click เพื่อ run program ได้
    2.1) กด Shift ค้าง และคลิกขวาที่ directory condo mailing record
    2.2) คลิกเลือกที่ Open powershell window here.
    2.3) ใช้คำสั่ง java -jar 6210406734.jar

### วิธีเริ่มต้นการใช้งาน
-   Admin เป็นผู้ดูแลโปรแกรมนี้
    - username: admin        
    - password:  1
    
- Staff เป็นผู้ดูแลส่วนกลาง
    - username: staff1    
    - password:  1
    
    - username: staff2
    - password: 2

- Resident เป็นผู้พักอาศัย
    - username: user01 
    - password: 111
    
    - username: user02
    - password: 222

    
