package com.example.sebastien.recproject;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Valentin on 17/11/2017.
 *
 * Description :
 * Item of a google search result
 *
 * Content :
 * Id : unique id for the data base
 * title : title of the item
 * displayLink : domain name
 */

@DatabaseTable(tableName = "GoogleResults")
public class GoogleResultItem implements Serializable{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField()
    public String title;
    @DatabaseField()
    public String displayLink;
    @DatabaseField()
    public String research = new String("");

    GoogleResultItem()
    {

    }

    GoogleResultItem(String title, String displayLink)
    {
        this.title = title;
        this.displayLink = displayLink;
    }

    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return displayLink;
    }

    public String getResearch(){
        return research;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDisplayLink(String displayLink)
    {
        this.displayLink = displayLink;
    }

    public void setResearch(String research){
        this.research = research;
    }


}
