package com.example.mariusz.zadanie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by mariusz on 05.08.16.
 */
public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "urlshortenerdb";
    private static final int DATABASE_VERSION = 1;

    private Dao<MyLongUrl, Integer> mUrlrDao = null;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MyLongUrl.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MyLongUrl.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<MyLongUrl, Integer> geturl() throws SQLException {
        if (mUrlrDao == null) {
            mUrlrDao = getDao(MyLongUrl.class);
        }
        return mUrlrDao;
    }

    @Override
    public void close() {
        mUrlrDao = null;
        super.close();
    }
}
