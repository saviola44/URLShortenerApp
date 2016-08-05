package com.example.mariusz.zadanie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mariusz.zadanie.Database.MyLongUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariusz on 05.08.16.
 */
public class URLAdapter extends ArrayAdapter {
    List<MyLongUrl> urlList = new ArrayList<>();

    public URLAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    public void setUrlList(List<MyLongUrl> urlList) {
        this.urlList = urlList;
    }

    @Override
    public Object getItem(int position) {
        return urlList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler handler;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.url_row_layout, parent, false);
            handler = new DataHandler();
            handler.longUrl = (TextView) row.findViewById(R.id.longUrlTV);
            handler.shortUrl = (TextView) row.findViewById(R.id.shortUrlTV);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler) row.getTag();
        }
        MyLongUrl myLongUrl = (MyLongUrl) getItem(position);
        handler.longUrl.setText(myLongUrl.getLongUrl());
        handler.shortUrl.setText(myLongUrl.getShortUrl());
        return row;
    }

    static class DataHandler{
        TextView longUrl;
        TextView shortUrl;
    }
}
