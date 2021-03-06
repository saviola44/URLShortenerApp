package com.example.mariusz.zadanie;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mariusz.zadanie.Database.MyDatabaseHelper;
import com.example.mariusz.zadanie.Database.MyLongUrl;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mariusz on 05.08.16.
 */
public class LoadURLFromDatabaseTask extends AsyncTask<Void, Void,Void> {
    List<MyLongUrl> urlList;
    Context context;
    LoadURLHandler handler;

    interface LoadURLHandler{
        void setList(List<MyLongUrl> urlList);
    }
    public LoadURLFromDatabaseTask(Context context, LoadURLHandler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        MyDatabaseHelper helper  = new MyDatabaseHelper(context);
        try {
            Dao<MyLongUrl, Integer> urlDao = helper.geturl();
            urlList = urlDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        handler.setList(urlList);
    }
}
