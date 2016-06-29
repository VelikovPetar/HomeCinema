package com.petar.homecinema;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeUI();

    }

    private void initializeUI() {
        // Title input field
        editTextTitle = (EditText) findViewById(R.id.title);
        int maxWidth = editTextTitle.getWidth();
        editTextTitle.setMaxWidth(maxWidth);

        // Actors input field
        editTextActors = (EditText) findViewById(R.id.actors);
        editTextActors.setMaxWidth(maxWidth);

        // Director input field
        editTextDirector = (EditText) findViewById(R.id.director);
        editTextDirector.setMaxWidth(maxWidth);

        // Genre input field
        editTextGenre = (EditText) findViewById(R.id.genre);
        editTextGenre.setMaxWidth(maxWidth);

        // Box number input field
        editTextBoxNumber = (EditText) findViewById(R.id.box_number);
        editTextBoxNumber.setMaxWidth(maxWidth);

        // Search button
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new SearchButtonHandler());

        // ListView where to display the matching results from the search
        resultsList = (ListView) findViewById(R.id.list_view);
    }

    /**
     * When pressed, search the database for entries matching the given parameters in the
     * appropriate input fields.
     */
    private class SearchButtonHandler implements View.OnClickListener {

        DatabaseAccess da;

        public SearchButtonHandler() {
            super();
            da = DatabaseAccess.getInstance(SearchActivity.this);
        }

        @Override
        public void onClick(View v) {
            String title = editTextTitle.getText().toString();
            String actors = editTextActors.getText().toString();
            String director = editTextDirector.getText().toString();
            String genre = editTextGenre.getText().toString();
            String boxNumber = editTextBoxNumber.getText().toString();

            String[] params = new String[]{title, actors, director, genre, boxNumber};
            Cursor cursor = da.search(params);
            MovieCursorAdapter mca = new MovieCursorAdapter(SearchActivity.this, cursor);
            resultsList.setAdapter(mca);
        }
    }

}
