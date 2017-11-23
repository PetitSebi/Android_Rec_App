package com.example.sebastien.recproject;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebastien on 21/11/2017.
 */

// Class used to define a marker with a title and coordinates

public class MapsMarkerCoordinates {

    private String title;
    private LatLng latLng;

    public MapsMarkerCoordinates(String title, LatLng latLng){
        this.title = title;
        this.latLng = latLng;
    }

    public String getTitle(){return title;}
    public LatLng getLatLng(){return latLng;}
}
