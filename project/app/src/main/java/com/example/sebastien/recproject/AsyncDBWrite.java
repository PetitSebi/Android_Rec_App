package com.example.sebastien.recproject;

import android.os.AsyncTask;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Valentin on 18/11/2017.
 */

//Add a result of a research into the data Base

public class AsyncDBWrite extends AsyncTask<Void, Integer, String> {
    Dao<GoogleResultItem, String> daoGoogleResult;
    MainActivity main;
    GoogleResultItem googleResultItemToAdd;

    AsyncDBWrite(MainActivity main, Dao<GoogleResultItem, String> daoGoogleResult, GoogleResultItem googleResultItemToAdd)
    {
        this.main = main;
        this.daoGoogleResult=daoGoogleResult;
        this.googleResultItemToAdd = googleResultItemToAdd;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... arg0) {
        List<GoogleResultItem> database ;
        GoogleResultItem item;
        boolean found = false;
        try {
            database = daoGoogleResult.queryForAll();
            for(int i=0; i < database.size(); i++)
            {
                item = database.get(i);
                if( item.getLink().equals(googleResultItemToAdd.getLink())  )
                {
                    found = true;
                }
            }

            if( !found)
                daoGoogleResult.create(googleResultItemToAdd);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        main.list.add(googleResultItemToAdd);
        return null;
    }

    protected void onProgressUpdate(Integer... values) {
    }

    protected void onPostExecute(String result) {

    }

}
