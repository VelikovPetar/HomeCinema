package com.petar.homecinema;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by petar on 6/14/16.
 */
public class SearchActivity extends Activity {

    private EditText editTextTitle;
    private EditText editTextActors;
    private EditText editTextDirector;
    private EditText editTextGenre;
    private EditText editTextBoxNumber;
    private Button searchButton;
    private ListView resultsList;

    private DatabaseAccess da;
    private MovieCursorAdapter mca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        da = DatabaseAccess.getInstance(this);
        initializeUI();

    }

    private void initializeUI() {
        // Title input field
        editTextTitle = (EditText) findViewById(R.id.title_search);
        int maxWidth = editTextTitle.getWidth();
        editTextTitle.setMaxWidth(maxWidth);

        // Actors input field
        editTextActors = (EditText) findViewById(R.id.actors_search);
        editTextActors.setMaxWidth(maxWidth);

        // Director input field
        editTextDirector = (EditText) findViewById(R.id.director_search);
        editTextDirector.setMaxWidth(maxWidth);

        // Genre input field
        editTextGenre = (EditText) findViewById(R.id.genre_search);
        editTextGenre.setMaxWidth(maxWidth);

        // Box number input field
        editTextBoxNumber = (EditText) findViewById(R.id.box_number_search);
        editTextBoxNumber.setMaxWidth(maxWidth);

        // Search button
        //When pressed, searches the database for entries mathing the entered parameters,
        //and displays them in a ListView
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String actors = editTextActors.getText().toString();
                String director = editTextDirector.getText().toString();
                String genre = editTextGenre.getText().toString();
                String boxNumber = editTextBoxNumber.getText().toString();

                String[] params = new String[]{title, actors, director, genre, boxNumber};
                Cursor cursor = da.search(params);
                mca.changeCursor(cursor);
            }
        });

        // ListView where to display the matching results from the search
        resultsList = (ListView) findViewById(R.id.list_view);

        // Initially display all movies in the database
        Cursor cursor = da.search(null);
        mca = new MovieCursorAdapter(SearchActivity.this, cursor);
        resultsList.setAdapter(mca);
    }

}
