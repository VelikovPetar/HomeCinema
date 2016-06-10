package com.petar.homecinema;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    DatabaseAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        da = DatabaseAccess.getInstance(this);
        da.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Basic testing of the inserting into the DB
     * @param view
     */
    public void addMovie(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Movie m = new Movie("Departed", "Mat Damon, Leonardo DiCaprio", "...", "drama, crime", 1);
        //dbh.insertMovie(m);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
    }

    /**
     * Basic testing of the searching in the DB
     * @param view
     */
    public void search(View view) {
        ArrayList<Movie> movies = da.searchByTitle("Alien");
        Toast.makeText(this, String.valueOf(movies.size()), Toast.LENGTH_LONG).show();
//        int i = 1;
//        for(Movie m : movies) {
//            Toast.makeText(this, i++ + ". " + m.toString(), Toast.LENGTH_LONG).show();
//        }
    }
}
