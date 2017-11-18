package com.example.sebastien.recproject;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Valentin on 17/11/2017.
 */

@DatabaseTable(tableName = "GoogleResults")
public class GoogleResultItem implements Serializable{
    @DatabaseField(id = true)
    public String title;
    //@DatabaseField()
    public String displayLink;

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


}
