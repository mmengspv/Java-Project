package project.java_63.service;

import project.java_63.models.Account;
import project.java_63.models.AccountBank;

import java.io.*;

public class AccountDataSource {
    private AccountBank accountBank;
    private String fileDirectory;
    private String fileName;

    public AccountDataSource(String fileDirectory, String fileName){
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted(){
        File file = new File(fileDirectory);
        if(!file.exists()){
            file.mkdirs();
        }
        String filePath = fileDirectory + File.separator + fileName;
        file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error create "+ filePath);
            }
        }
    }

    private void readData(){
        String filePath = fileDirectory + File.separator + fileName;
        File file = new File(filePath);

        BufferedReader buffer = null;
        try {
            FileReader fileReader = new FileReader(file);
            buffer = new BufferedReader(fileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                String data[] = line.split(", ");
                String userName = data[0].trim();
                String password = data[1].trim();
                String name = data[2].trim();
                String surname= data[3].trim();
                String phoneNumber = data[4].trim();
                String role = data[5].trim();
                String date = data[6].trim();
                String image = data[7].trim();
                Account acc = new Account(userName, password, name, surname, phoneNumber, role);
                acc.setDate(date);
                acc.setImage(image);
                accountBank.addAccount(acc);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error to opening file " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading data from user");
        } finally {
            try {
                if(fileName != null){
                    buffer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing files");
            }
        }
    }

    public void setAccountData(AccountBank accountBank){
        String filePath = fileDirectory + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        BufferedWriter buffer = null;
        try {
            // write file
            fileWriter = new FileWriter(file);
            buffer = new BufferedWriter(fileWriter);

            for(Account acc: accountBank.getAccountList()){
                String line = acc.getUsername() + ", "
                            + acc.getPassword() + ", "
                            + acc.getName() + ", "
                            + acc.getSurname() + ", "
                            + acc.getPhoneNumber()+ ", "
                            + acc.getRole() + ", "
                            + acc.getDateTime() + ", "
                            + acc.getImage();
                buffer.append(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error opening files");
        }finally {
            try {
                if(fileWriter != null){
                    buffer.close();
                }
            } catch (IOException e) {
                System.err.println("Error write " + filePath);
            }
        }
    }

    public AccountBank getAccountData(){
        accountBank = new AccountBank();
        readData();
        return accountBank;
    }
}
