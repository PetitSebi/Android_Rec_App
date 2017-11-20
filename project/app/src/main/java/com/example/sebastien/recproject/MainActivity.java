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

    private DBHelper dbHelper;
    private Dao<GoogleResultItem, String> googleResultItemDao;

    public ArrayList<GoogleResultItem> list = new ArrayList<GoogleResultItem>();

    private ListFragment listFragment;



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
                //
                return true;
            case R.id.options:
                //
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

        InfoFragment infoFragment = new InfoFragment();
        fragmentTransaction.replace(R.id.container, infoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void callMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        //intent.putExtra(Intent.EXTRA_TEXT, "Data");
        this.startActivity(intent);
    }

    @Override
    public void addToBDDGoogleResultItem(GoogleResultItem item) {
        AsyncDBWrite writeInDbBottle = new AsyncDBWrite(this, googleResultItemDao, item );
        writeInDbBottle.execute();
    }

    @Override
    public void displayGoogleResult(ArrayList<GoogleResultItem> list) {
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
