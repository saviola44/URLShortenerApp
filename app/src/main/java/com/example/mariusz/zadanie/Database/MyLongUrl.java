package com.example.mariusz.zadanie.Database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mariusz on 05.08.16.
 */
@DatabaseTable(tableName = "urls", daoClass = BaseDaoImpl.class)
public class MyLongUrl {
    @DatabaseField
    private String longUrl;
    @DatabaseField(columnName = "shortUrl", id = true)
    private String id;

    public MyLongUrl() {
    }

    public MyLongUrl(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.id = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return id;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.id = shortUrl;
    }
}
