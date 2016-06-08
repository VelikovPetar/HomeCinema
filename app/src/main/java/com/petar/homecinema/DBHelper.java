package com.petar.homecinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by petar on 6/4/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MovieDatabase.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "_id";
    public static final String MOVIES_COLUMN_TITLE = "title";
    public static final String MOVIES_COLUMN_ACTORS = "actors";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_GENRE = "genre";
    public static final String MOVIES_COLUMN_BOX = "box";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabase = "create table " + MOVIES_TABLE_NAME + "(" + MOVIES_COLUMN_ID + " integer primary key, "
                + MOVIES_COLUMN_TITLE + " text not null, " + MOVIES_COLUMN_ACTORS + " text, "
                + MOVIES_COLUMN_DIRECTOR + " text, " + MOVIES_COLUMN_GENRE + " text, "
                + MOVIES_COLUMN_BOX + " integer not null);";
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO:
    }

    public void insertMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIES_COLUMN_TITLE, movie.title);
        contentValues.put(MOVIES_COLUMN_ACTORS, movie.actors);
        contentValues.put(MOVIES_COLUMN_DIRECTOR, movie.director);
        contentValues.put(MOVIES_COLUMN_GENRE, movie.genre);
        contentValues.put(MOVIES_COLUMN_BOX, movie.box);
        db.insert(MOVIES_TABLE_NAME, null, contentValues);
    }

    /**
     *
     * @param title keyword to be searched
     * @return ArrayList containing movies which have the parameter title in their "title" column
     * TODO: To be tested!
     * TODO: To be modified to directly display the fetched data into a ListView. (MAYBE)
     */
    public ArrayList<Movie> searchByTitle(String title) {
        ArrayList<Movie> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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

    public long numberOfMovies() {
        return getReadableDatabase().rawQuery("select * from " + MOVIES_TABLE_NAME, null).getCount();
    }
}
