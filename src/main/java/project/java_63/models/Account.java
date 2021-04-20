package project.java_63.models;

public class Account {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    private String date = "-";
    private String role;
    private String image = "data/staff_images/default_staff_icon.png";

    public Account(String username, String password, String name, String surname, String phoneNumber, String role){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getRole() {
        return role;
    }
    public String getDateTime(){
        return date;
    }
    public String getDate() { return date; }
    public String getImage() { return image; }

    public void setDate(String date) {this.date = date;}
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(String role) { this.role = role; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", date=" + date +
                ", role='" + role + '\'' +
                '}';
    }



}



