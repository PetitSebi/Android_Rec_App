package com.example.sebastien.recproject;

import android.os.AsyncTask;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Valentin on 20/11/2017.
 *
 * Description :
 * Delete the whole local data base
 */

public class AsyncDBDelete extends AsyncTask<Void, Integer, String> {

    Dao<GoogleResultItem, String> daoGoogleResult;
    MainActivity main;
    ArrayList<GoogleResultItem> list = new ArrayList<GoogleResultItem>();


    AsyncDBDelete(MainActivity main, Dao<GoogleResultItem, String> daoGoogleResult, ArrayList<GoogleResultItem> list) {
        this.main = main;
        this.daoGoogleResult = daoGoogleResult;
        //Copy of the data base
        this.list = new ArrayList<GoogleResultItem>(list);
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... arg0) {
        // Delete the data base
        try {
            daoGoogleResult.delete(this.list);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(Integer... values) {
    }

    protected void onPostExecute(String result) {
        //Empty the ArrayList of the MainActivity which is a copy of the data base
        main.list.clear();
        //Update and display the ListFragment to show that list is empty
        main.displayGoogleResultList();
    }
}

