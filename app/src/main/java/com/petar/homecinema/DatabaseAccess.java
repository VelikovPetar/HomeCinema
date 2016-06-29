package com.petar.homecinema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by petar on 6/10/16.
 */
public class DatabaseAccess {

    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "_id";
    public static final String MOVIES_COLUMN_TITLE = "title";
    public static final String MOVIES_COLUMN_ACTORS = "actors";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_GENRE = "genre";
    public static final String MOVIES_COLUMN_BOX = "box";

    private static DatabaseAccess instance;
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    public static synchronized DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    private DatabaseAccess(Context context) {
        openHelper = new DatabaseAssetHelper(context);
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void close() {
        if(database != null) {
            database.close();
        }
    }

    /**
     * Method that searches the database for instances that contain the entered parameters
     * @param params Entered parameters to search by
     * @return Cursor pointing to the fetched rows from the database
     */
    public Cursor search(String[] params) {
        String whereQuery = "title like ? AND actors like ? AND director like ? AND genre like ? AND box like ?";
        for(int i = 0; i < params.length; ++i) {
            params[i] = String.format("%%%s%%", params[i]);
        }
        Cursor cursor;
        SQLiteDatabase db = openHelper.getReadableDatabase();
        cursor = db.query(MOVIES_TABLE_NAME, new String[]{MOVIES_COLUMN_TITLE, MOVIES_COLUMN_ACTORS, MOVIES_COLUMN_DIRECTOR, MOVIES_COLUMN_GENRE, MOVIES_COLUMN_BOX}, whereQuery, params, null, null, null);
        return cursor;
    }

}
