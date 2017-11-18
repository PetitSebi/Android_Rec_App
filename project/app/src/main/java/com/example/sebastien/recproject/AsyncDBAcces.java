package com.example.sebastien.recproject;

import android.os.AsyncTask;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 18/11/2017.
 */

//Acces to the data base
//Return a list a the former results of the google research

public class AsyncDBAcces extends AsyncTask<Void, Integer, String> {

    Dao<GoogleResultItem, String> daoGoogleResult;
    MainActivity main;

    AsyncDBAcces(MainActivity main, Dao<GoogleResultItem, String> daoGoogleResult)
    {
        this.main = main;
        this.daoGoogleResult=daoGoogleResult;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... arg0) {
        main.list = new ArrayList<GoogleResultItem>();
        try {
            //int nb_bouteilles = bottleDao.countOf();
            List<GoogleResultItem> listQuery = daoGoogleResult.queryForAll();
            for( GoogleResultItem b:listQuery )
            {
                main.list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(Integer... values) {
    }

    protected void onPostExecute(String result) {
        main.displayGoogleResultList();
    }

}
