package project.java_63.service;

import project.java_63.models.Account;
import project.java_63.models.AccountBank;
import project.java_63.models.AccountStaff;

import java.io.*;

public class AccountStaffDataSource {
    private String fileDirectoryName;
    private String fileName;
    private AccountBank accountBank;

    public AccountStaffDataSource(String fileDirectoryName, String fileName){
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    public void checkFileIsExisted(){
        File file = new File(fileDirectoryName);
        if(!file.exists()){
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Can not create "+filePath);
            }
        }
    }

    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
            String[] data = line.split(", ");
            String username = data[0].trim();
            String password = data[1].trim();
            String name = data[2].trim();
            String surname = data[3].trim();
            String phoneNumber = data[4].trim();
            String role = data[5].trim();
            String date = data[6].trim();
            String block = data[7].trim();
            int tryToLogin = Integer.parseInt(data[8].trim());
            String image = data[9].trim();
            AccountStaff accStaff = new AccountStaff(username, password, name, surname, phoneNumber, role);
            accStaff.setTotalTryToLogin(tryToLogin);
            accStaff.setBlock(block);
            accStaff.setDate(date);
            accStaff.setImage(image);
            accountBank.addAccount(accStaff);
        }
        reader.close();
    }

    public AccountBank getAccountBank(){
        accountBank = new AccountBank();
        try {
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("Can not reading "+fileName);
        }
        return accountBank;
    }

    public void setAccountData(AccountBank accountBank){
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(Account acc: accountBank.getAccountList()){
                AccountStaff accStaff = (AccountStaff)acc;

                String line = accStaff.getUsername() + ", "
                            + accStaff.getPassword() + ", "
                            + accStaff.getName() + ", "
                            + accStaff.getSurname() + ", "
                            + accStaff.getPhoneNumber() + ", "
                            + accStaff.getRole() + ", "
                            + accStaff.getDateTime() + ", "
                            + accStaff.getBlock() + ", "
                            + accStaff.getCountTryToLogin() + ", "
                            + accStaff.getImage();
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write "+ filePath);
        }
    }

}
