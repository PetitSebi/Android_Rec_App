package com.example.sebastien.recproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListFragment.OnFragmentInteractionListener, InfoFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();

        ListFragment listFragment = new ListFragment();
        fragmentTransaction.add(R.id.container, listFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view) {
    }

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
