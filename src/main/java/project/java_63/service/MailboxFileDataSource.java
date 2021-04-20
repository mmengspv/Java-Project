package project.java_63.service;

import project.java_63.models.Document;
import project.java_63.models.Letter;
import project.java_63.models.Mailbox;
import project.java_63.models.Parcel;

import java.io.*;

public class MailboxFileDataSource {
    private String fileDirectoryName;
    private String fileName;
    private Mailbox mailboxList;

    public MailboxFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error create " + filePath);
            }
        }
    }

    private void readMailboxData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String data[] = line.split(", ");
            String type = data[0].trim();
            String nameReceiver = data[1].trim();
            String surnameReceiver = data[2].trim();
            String roomNumber = data[3].trim();
            String nameSender = data[4].trim();
            String surnameSender = data[5].trim();
            String width = data[6].trim();
            String height = data[7].trim();
            String image = data[8].trim();
            String dateIn = data[9].trim();
            String status = data[10].trim();
            String staffReceiver = data[11].trim();
            String realReceiver = data[12].trim();
            String staffOut = data[13].trim();
            String dateOut = data[14].trim();
            if (type.equals("Letter")) {
                Letter letter = new Letter(type, nameReceiver, surnameReceiver, roomNumber, nameSender, surnameSender, width, height);
                letter.setStatus(status);
                letter.setImage(image);
                letter.setDateIn(dateIn);
                letter.setStaffReceiver(staffReceiver);
                letter.setPickupBy(realReceiver);
                letter.setStaffOut(staffOut);
                letter.setDateOut(dateOut);
                mailboxList.addSuppliesToLocker(letter);
            } else if (type.equals("Document")) {
                String importance = data[15].trim();
                Document document = new Document(type, nameReceiver, surnameReceiver, roomNumber, nameSender, surnameSender, width, height, importance);
                document.setImage(image);
                document.setStatus(status);
                document.setDateIn(dateIn);
                document.setStaffReceiver(staffReceiver);
                document.setPickupBy(realReceiver);
                document.setStaffOut(staffOut);
                document.setDateOut(dateOut);
                mailboxList.addSuppliesToLocker(document);
            } else if (type.equals("Parcel")) {
                String delivery = data[15].trim();
                String trackingNumber = data[16].trim();
                Parcel parcel = new Parcel(type, nameReceiver, surnameReceiver, roomNumber, nameSender, surnameSender, width, height, delivery, trackingNumber);
                parcel.setImage(image);
                parcel.setStatus(status);
                parcel.setStaffOut(staffOut);
                parcel.setDateIn(dateIn);
                parcel.setStaffReceiver(staffReceiver);
                parcel.setPickupBy(realReceiver);
                parcel.setDateOut(dateOut);
                mailboxList.addSuppliesToLocker(parcel);
            }
        }
        reader.close();
    }

    public Mailbox getMailboxList() {
        try {
            mailboxList = new Mailbox();
            readMailboxData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return mailboxList;
    }

    public void setMailboxListData(Mailbox mailboxList) {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Letter letter : mailboxList.getMailboxList()) {
                String line = letter.getType() + ", "
                        + letter.getNameReceiver() + ", "
                        + letter.getSurnameReceiver() + ", "
                        + letter.getRoomNumber() + ", "
                        + letter.getNameSender() + ", "
                        + letter.getSurnameSender() + ", "
                        + letter.getWidth() + ", "
                        + letter.getHeight() + ", "
                        + letter.getImage() + ", "
                        + letter.getDateIn() + ", "
                        + letter.getStatus() + ", "
                        + letter.getStaffReceiver() + ", "
                        + letter.getPickupBy() + ", "
                        + letter.getStaffOut() + ", "
                        + letter.getDateOut();
                if (letter.getType().equals("Document")) {
                    Document document = (Document) letter;
                    line += ", " + document.getImportance();
                } else if (letter.getType().equals("Parcel")) {
                    Parcel parcel = (Parcel) letter;
                    line += ", " + parcel.getDelivery() + ", " + parcel.getTrackingNumber();
                }
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
