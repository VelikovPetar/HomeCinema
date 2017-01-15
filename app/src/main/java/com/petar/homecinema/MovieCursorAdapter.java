package com.petar.homecinema;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by petar on 6/29/16.
 */
public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //TODO: Set pleasant colors
        if((cursor.getPosition() & 1) == 0) {
            view.setBackgroundResource(R.drawable.round_corner_color1);
        } else {
            view.setBackgroundResource(R.drawable.round_corner_color2);
        }

        TextView titleView = (TextView) view.findViewById(R.id.movie_list_item_title);
        TextView actorsView = (TextView) view.findViewById(R.id.movie_list_item_actors);
        TextView directorView = (TextView) view.findViewById(R.id.movie_list_item_director);
        TextView genreView = (TextView) view.findViewById(R.id.movie_list_item_genre);
        TextView boxNumberView = (TextView) view.findViewById(R.id.movie_list_item_boxNumber);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAccess.MOVIES_COLUMN_TITLE));
        String actors = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAccess.MOVIES_COLUMN_ACTORS));
        String director = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAccess.MOVIES_COLUMN_DIRECTOR));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAccess.MOVIES_COLUMN_GENRE));
        String boxNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAccess.MOVIES_COLUMN_BOX));

        titleView.setText(title);

        actorsView.setText(actors);
        actorsView.setVisibility(View.GONE);

        directorView.setText(director);
        directorView.setVisibility(View.GONE);

        genreView.setText(genre);
        genreView.setVisibility(View.GONE);

        boxNumberView.setText(boxNumber);
        boxNumberView.setVisibility(View.GONE);
    }



}
