package com.example.sebastien.recproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap = null;
    private RequestQueue queue;
    private ArrayList<MapsMarkerCoordinates> listMapsMarkerCoordinates = new ArrayList<>();
    private ArrayList<String> listAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Create an HTTP Request queue
        queue = Volley.newRequestQueue(this);
        // Get the list of physical addresses, and convert them into coordinates
        listAddresses = (ArrayList<String>) getIntent().getSerializableExtra("listAddresses");
        addressesToCoordinates();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add all the markers to the Map
        addMarkers();
    }

    public void geocodingRequest(String position){
        // We are using the Geocoding API to convert the full_address to coordinates
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+position+"&key=AIzaSyDPTLi7pp4i37tr2RzsH0JBl_L5td6FyvQ";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Get the coordinates from the JSON response
                            JSONObject geocodingResult = new JSONObject(response);
                            JSONObject results = geocodingResult.getJSONArray("results").getJSONObject(0);
                            JSONObject geometry = results.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");
                            LatLng coordinates = new LatLng(location.getDouble("lat"),location.getDouble("lng"));
                            // Get the title
                            String title = results.getString("formatted_address");
                            // Add the coordinates to the list of coordinates
                            listMapsMarkerCoordinates.add(new MapsMarkerCoordinates(title,coordinates));
                            // refresh the map to add the new marker
                            refreshMap();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Todo
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void addressesToCoordinates(){
        for( String address : listAddresses){
            // An empty address field isn't processed
            if(!address.equals("")){
                // Convert the address into coordinates, and add it to the listOfCoordinates
                geocodingRequest(address);

            }
        }
    }

    public void addMarkers(){
        for(MapsMarkerCoordinates mmc : listMapsMarkerCoordinates){
                mMap.addMarker(new MarkerOptions()
                        .position(mmc.getLatLng())
                        .title(mmc.getTitle()));
        }
    }

    public void refreshMap(){
        if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mMap.clear();
            addMarkers();
        }
    }
}
