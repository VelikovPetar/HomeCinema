package com.petar.homecinema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public static DatabaseAccess getInstance(Context context) {
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

    public ArrayList<Movie> searchByTitle(String title) {
        ArrayList<Movie> result = new ArrayList<>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String getMoviesByTitle = "select * from " + MOVIES_TABLE_NAME + " where " + MOVIES_COLUMN_TITLE + " like " + "'%" + title + "%';";
        Cursor cursor = db.rawQuery(getMoviesByTitle, null);
        //cursor = db.query(MOVIES_TABLE_NAME, new String[]{MOVIES_COLUMN_TITLE, MOVIES_COLUMN_ACTORS, MOVIES_COLUMN_DIRECTOR, MOVIES_COLUMN_GENRE, MOVIES_COLUMN_BOX}, "title like '%?%'", new String[]{title}, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Movie movie = new Movie(cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_ACTORS)), cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_DIRECTOR)),
                    cursor.getString(cursor.getColumnIndex(MOVIES_COLUMN_GENRE)), cursor.getInt(cursor.getColumnIndex(MOVIES_COLUMN_BOX)));
            result.add(movie);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
}
