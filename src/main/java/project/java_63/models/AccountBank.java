package project.java_63.models;

import java.util.ArrayList;

public class AccountBank {
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public AccountBank(){ accounts = new ArrayList<>(); }

    public void addAccount(Account acc){
        accounts.add(acc);
    }

    public boolean checkUser(String username, String password){
        for(Account a: accounts){
            if(a.getUsername().equals(username) && a.getPassword().equals(password)) {
                currentAccount = a;
                return true;
            }
        }
        currentAccount = null;
        return false;
    }

    public boolean findUserName(String userName){
        for(Account acc: accounts){
            if(acc.getUsername().equals(userName)){
                return true;
            }
        }
        return false;
    }


    public Account getCurrentAccount(){
        return currentAccount;
    }

    public ArrayList<Account> getAccountList(){ return accounts;}

//    public ArrayList<AccountStaff> getAccountStaffList(){return accounts.;}

}
