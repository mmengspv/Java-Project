package project.java_63.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Mailbox {
    private ArrayList<Letter> mailboxList;

    public Mailbox(){
        mailboxList = new ArrayList<>();
    }

    public void addSuppliesToLocker(Letter letter){
        mailboxList.add(letter);
    }

    public void showSupplies(){
        for(Letter l: mailboxList){
            System.out.println(l.toString());
        }
    }


    public ArrayList<Letter> getMailboxList(){
        return mailboxList;
    }



}
