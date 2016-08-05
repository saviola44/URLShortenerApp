package com.example.mariusz.zadanie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mariusz.zadanie.Database.MyDatabaseHelper;
import com.example.mariusz.zadanie.Database.MyLongUrl;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mariusz on 03.08.16.
 */
public class Fragment2 extends Fragment {
    ListView urlListView;
    List<MyLongUrl> urlList;
    URLAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        getActivity().setTitle("Fragment2");
        urlListView = (ListView) view.findViewById(R.id.urllLV);
        adapter = new URLAdapter(getActivity().getApplicationContext(), R.layout.url_row_layout);
        urlListView.setAdapter(adapter);
        loadDataFromDatabase();

        urlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openUrlAdressInBrowser(urlList.get(position));
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void loadDataFromDatabase(){
        LoadURLFromDatabaseTask task = new LoadURLFromDatabaseTask(getActivity().getApplicationContext(), adapter);
        task.execute();
    }


    public void openUrlAdressInBrowser(MyLongUrl url){
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.getLongUrl()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity().getApplicationContext(), "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
