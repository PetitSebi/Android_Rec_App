package com.example.sebastien.recproject;

import java.util.ArrayList;

/**
 * Created by Sebastien on 18/11/2017.
 */

// Class containing a list of contacts related to a specified domain name

public class WhoisResult {

    private ArrayList<WhoisResultItem> contacts;

    public WhoisResult(ArrayList<WhoisResultItem> listContacts){
        this.contacts = listContacts;
    }

    public ArrayList<WhoisResultItem> getContacts(){return  contacts;}

    public void setContacts(ArrayList<WhoisResultItem> contacts){
        this.contacts = contacts;
    }
}
