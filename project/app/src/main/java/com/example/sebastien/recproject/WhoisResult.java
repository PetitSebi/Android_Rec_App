package com.example.sebastien.recproject;

import java.util.ArrayList;

/**
 * Created by Sebastien on 18/11/2017.
 */

public class WhoisResult {

    private ArrayList<WhoisResultItem> contacts;

    public WhoisResult(ArrayList<WhoisResultItem> listContacts){
        this.contacts = listContacts;
    }

    public ArrayList<WhoisResultItem> getContacts(){return  contacts;}
}