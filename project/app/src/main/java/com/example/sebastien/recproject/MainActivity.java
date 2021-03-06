package com.example.sebastien.recproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, InfoFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener {

    //Data base manipulation
    private DBHelper dbHelper;
    private Dao<GoogleResultItem, String> googleResultItemDao;

    //Global variable to save the list
    public ArrayList<GoogleResultItem> list = new ArrayList<GoogleResultItem>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        //Remove the name of the application from the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initialize elements to interact with the database
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        googleResultItemDao = dbHelper.getGoogleResultDao();

        //Initialize the screen of the activity, display the ListFragment
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        ListFragment listFragment = new ListFragment();
        fragmentTransaction.replace(R.id.container, listFragment);
        fragmentTransaction.commit();

        //Read the data base and display it in the ListFragment
        AsyncDBAcces testAsynchrone = new AsyncDBAcces(this, googleResultItemDao);
        testAsynchrone.execute();

    }


    //##############################################################################################
    //Toolbar functions
    //##############################################################################################

    //Set the content of the file menu.xml into the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actualiser:
                //"Actualiser" button of the toolbar
                //Read all the data base and display it
                AsyncDBAcces testAsynchrone = new AsyncDBAcces(this, googleResultItemDao);
                testAsynchrone.execute();
                return true;
            case R.id.options:
                //Copy the data base into the list variable
                AsyncDBAcces getdbAsynchrone = new AsyncDBAcces(this, googleResultItemDao);
                getdbAsynchrone.execute();
                //Clear the data base
                AsyncDBDelete delAsynchrone = new AsyncDBDelete(this, googleResultItemDao, list);
                delAsynchrone.execute();


                return true;

            default: // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //##############################################################################################
    //Interface to switch from a fragment to another
    //##############################################################################################

    @Override
    public void callInfoFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        // Replace the current fragment with infoFragment
        InfoFragment infoFragment = new InfoFragment();
        fragmentTransaction.replace(R.id.container, infoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public ArrayList<String> getListDomains() {
        // get the domain names from the list of GoogleResultItem
        ArrayList<String> listDomains = new ArrayList<>();
        for(GoogleResultItem item: list){
            listDomains.add(item.getLink());
        }
        return listDomains;
    }

    @Override
    public void callMapsActivity(ArrayList<String> listAddresses) {
        Intent intent = new Intent(this, MapsActivity.class);
        // Transfer a list of physical addresses to the MapsActivity
        intent.putExtra("listAddresses", listAddresses);
        this.startActivity(intent);
    }

    @Override
    public void addToBDDGoogleResultItem(GoogleResultItem item) {
        AsyncDBWrite writeInDbBottle = new AsyncDBWrite(this, googleResultItemDao, item );
        writeInDbBottle.execute();
    }

    @Override
    public void displayGoogleResult(ArrayList<GoogleResultItem> list) {
        // Update the list to display and recall the ListFragment
        this.list = list;
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        ListFragment listFragment = new ListFragment();
        listFragment.updateList(this.list);
        fragmentTransaction.replace(R.id.container, listFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void callDetailsFragment(GoogleResultItem googleItem) {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        // Send the GoogleResultItem to the DetailsFragment through a method
        detailsFragment.saveGoogleItem(googleItem);
        // Replace Fragment
        fragmentTransaction.replace(R.id.container, detailsFragment);
        fragmentTransaction.addToBackStack("detailsFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void callListFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        ListFragment listFragment = ListFragment.newInstance(list);
        fragmentTransaction.replace(R.id.container, listFragment);
        fragmentTransaction.commit();
    }


    //##############################################################################################
    //DataBase interactions
    //##############################################################################################


    //Display the BDD at the launch of the application, C.f. AsyncDBAcces
    public void displayGoogleResultList()
    {
    // Display the list of previous researched
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        ListFragment listFragment = new ListFragment();
        listFragment.updateList(list);
        fragmentTransaction.replace(R.id.container, listFragment);
        fragmentTransaction.commit();

    }


}
