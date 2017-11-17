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
import android.view.View;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, InfoFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get fragment manager
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();

        //By default when the app start, the ListFragment is displayed
        ListFragment listFragment = new ListFragment();
        fragmentTransaction.add(R.id.container, listFragment);
        fragmentTransaction.commit();

        //Initialize toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        //Remove the name of the application from the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    public void callDetailsFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();

        DetailsFragment detailsFragment = new DetailsFragment();
        fragmentTransaction.replace(R.id.container, detailsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void callListFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();

        ListFragment listFragment = new ListFragment();
        fragmentTransaction.replace(R.id.container, listFragment);
        fragmentTransaction.commit();
    }
}
