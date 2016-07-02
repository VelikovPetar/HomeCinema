package com.petar.homecinema;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by petar on 7/2/16.
 */
public class InsertActivity extends Activity {

    private EditText editTextTitle;
    private EditText editTextActors;
    private EditText editTextDirector;
    private EditText editTextGenre;
    private EditText editTextBoxNumber;
    private Button insertButton;

    private DatabaseAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        da = DatabaseAccess.getInstance(this);
        initializeUI();
    }

    private void initializeUI() {
        // Title input field
        editTextTitle = (EditText) findViewById(R.id.title_insert);
        int maxWidth = editTextTitle.getWidth();
        editTextTitle.setMaxWidth(maxWidth);

        // Actors input field
        editTextActors = (EditText) findViewById(R.id.actors_insert);
        editTextActors.setMaxWidth(maxWidth);

        // Director input field
        editTextDirector = (EditText) findViewById(R.id.director_insert);
        editTextDirector.setMaxWidth(maxWidth);

        // Genre input field
        editTextGenre = (EditText) findViewById(R.id.genre_insert);
        editTextGenre.setMaxWidth(maxWidth);

        // Box number input field
        editTextBoxNumber = (EditText) findViewById(R.id.box_number_insert);
        editTextBoxNumber.setMaxWidth(maxWidth);

        // Insert button
        // When pressed, inserts new movie into the database, with the entered attributes
        // The values for 'Title' and 'Box number' cannot be omitted
        insertButton = (Button) findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String actors = editTextActors.getText().toString();
                String director = editTextDirector.getText().toString();
                String genre = editTextGenre.getText().toString();
                String boxNumber = editTextBoxNumber.getText().toString();

                // Check if required parameters are entered
                if(title.trim().equals("") && boxNumber.trim().equals("")) {
                    Toast.makeText(InsertActivity.this, "You must enter Title and Box Number!", Toast.LENGTH_LONG).show();
                    return;
                } else if(title.trim().equals("")) {
                    Toast.makeText(InsertActivity.this, "You must enter Title!", Toast.LENGTH_LONG).show();
                    return;
                } else if(boxNumber.trim().equals("")) {
                    Toast.makeText(InsertActivity.this, "You must enter BoxNumber!", Toast.LENGTH_LONG).show();
                    return;
                }

                String[] params = new String[]{title, actors, director, genre, boxNumber};
                long error = da.insert(params);
                if(error == -1) {
                    Toast.makeText(InsertActivity.this, "An error occurred while inserting the movie to the database!\nPlease try again.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InsertActivity.this, "Successfully inserted movie!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
