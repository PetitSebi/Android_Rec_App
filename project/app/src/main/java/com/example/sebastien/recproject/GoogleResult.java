package com.example.sebastien.recproject;

import java.util.ArrayList;

/**
 * Created by Valentin on 18/11/2017.
 *
 * Used to get the result of the google researched using a Json object
 */

public class GoogleResult {

    //List of results
    ArrayList<GoogleResultItem> items = new ArrayList<>();

    public void addItem(String title, String displayLink)
    {
        items.add(new GoogleResultItem(title, displayLink));
    }

    public ArrayList<GoogleResultItem> getItems()
    {
        return items;
    }
}
