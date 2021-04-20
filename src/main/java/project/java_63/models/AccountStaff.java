package project.java_63.models;

public class AccountStaff extends Account {
    private String block = "activate";
    private int totalTryToLogin = 0;

    public AccountStaff(String username, String password, String name, String surname, String phoneNumber, String role) {
        super(username, password, name, surname, phoneNumber, role);
    }

    public void countTryToLogin(){
        totalTryToLogin += 1;
    }

    public void setBlock(String block) {
        this.block = block;
    }
    public int getCountTryToLogin() { return totalTryToLogin; }
    public String getCountTryToLoginString(){return String.valueOf(totalTryToLogin);}

    public String getBlock() {
        return block;
    }

    public void setTotalTryToLogin(int totalTryToLogin) { this.totalTryToLogin = totalTryToLogin; }
}
