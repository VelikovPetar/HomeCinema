package com.petar.homecinema;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    private int clickedItemPosition;

    private DatabaseAccess da;
    private MovieCursorAdapter mca;
    private Cursor allMoviesCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        da = DatabaseAccess.getInstance(this);
        allMoviesCursor = da.search(null);
        mca = new MovieCursorAdapter(SearchActivity.this, allMoviesCursor);
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
        //When pressed, searches the database for entries matching the entered parameters,
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
        resultsList.setAdapter(mca);

        clickedItemPosition = -1;
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //TODO: Refactor code

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView actorsView = (TextView) view.findViewById(R.id.movie_list_item_actors);
                TextView directorView = (TextView) view.findViewById(R.id.movie_list_item_director);
                TextView genreView = (TextView) view.findViewById(R.id.movie_list_item_genre);
                TextView boxNumberView = (TextView) view.findViewById(R.id.movie_list_item_boxNumber);

                if(clickedItemPosition != -1 && clickedItemPosition != position) {
                    // If there was a previously clicked item, clicking on a new item, results in hiding the extra info for the previous item,
                    // and displaying extra info for the new item
                    int firstVisibleViewPosition = resultsList.getFirstVisiblePosition();
                    int lastVisibleViewPosition = resultsList.getLastVisiblePosition();

                    if(clickedItemPosition >= firstVisibleViewPosition && clickedItemPosition <= lastVisibleViewPosition) {
                        int newPosition = clickedItemPosition - firstVisibleViewPosition;

                        View previousClickedView = resultsList.getChildAt(newPosition);

                        TextView previousActorsView = (TextView) previousClickedView.findViewById(R.id.movie_list_item_actors);
                        TextView previousDirectorView = (TextView) previousClickedView.findViewById(R.id.movie_list_item_director);
                        TextView previousGenreView = (TextView) previousClickedView.findViewById(R.id.movie_list_item_genre);
                        TextView previousBoxNumberView = (TextView) previousClickedView.findViewById(R.id.movie_list_item_boxNumber);

                        previousActorsView.setVisibility(View.GONE);
                        previousDirectorView.setVisibility(View.GONE);
                        previousGenreView.setVisibility(View.GONE);
                        previousBoxNumberView.setVisibility(View.GONE);
                    }

                    actorsView.setVisibility(View.VISIBLE);
                    directorView.setVisibility(View.VISIBLE);
                    genreView.setVisibility(View.VISIBLE);
                    boxNumberView.setVisibility(View.VISIBLE);

                    clickedItemPosition = position;

                } else if(clickedItemPosition == position) {
                    // When clicking on an item that already displays the extra info, hide the extra info
                    toggleView(actorsView, directorView, genreView, boxNumberView);
                } else {
                    // The first item clicked, results in displaying the extra info for that item
                    actorsView.setVisibility(View.VISIBLE);
                    directorView.setVisibility(View.VISIBLE);
                    genreView.setVisibility(View.VISIBLE);
                    boxNumberView.setVisibility(View.VISIBLE);

                    clickedItemPosition = position;
                }
            }

            private void toggleView(TextView actorsView, TextView directorView, TextView genreView, TextView boxNumberView) {
                if(actorsView.getVisibility() == View.GONE) {
                    actorsView.setVisibility(View.VISIBLE);
                } else {
                    actorsView.setVisibility(View.GONE);
                }
                if(directorView.getVisibility() == View.GONE) {
                    directorView.setVisibility(View.VISIBLE);
                } else {
                    directorView.setVisibility(View.GONE);
                }
                if(genreView.getVisibility() == View.GONE) {
                    genreView.setVisibility(View.VISIBLE);
                } else {
                    genreView.setVisibility(View.GONE);
                }
                if(boxNumberView.getVisibility() == View.GONE) {
                    boxNumberView.setVisibility(View.VISIBLE);
                } else {
                    boxNumberView.setVisibility(View.GONE);
                }
            }

        });
    }

}
