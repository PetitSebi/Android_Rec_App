package com.example.sebastien.recproject;

/**
 * Created by Sebastien on 18/11/2017.
 */

// Class gathering some information about each contact

public class WhoisResultItem {

    private String type;
    private String name;
    private String organization;
    private String phone;
    private String email;
    private String full_address;

    public WhoisResultItem(String type, String name, String organization, String phone, String email, String full_address){
        this.type = type;
        this.name = name;
        this.organization = organization;
        this.phone = phone;
        this.email = email;
        this.full_address = full_address;
    }

    // Getters
    public String getType(){ return type;}
    public String getName(){ return name;}
    public String getOrganization(){ return organization;}
    public String getPhone(){ return phone;}
    public String getEmail(){ return email;}
    public String getFull_address(){ return full_address;}
}
