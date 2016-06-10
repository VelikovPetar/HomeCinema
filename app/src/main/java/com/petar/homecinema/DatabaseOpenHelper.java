package com.petar.homecinema;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by petar on 6/10/16.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "MovieDatabase.sqlite";
    private static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
