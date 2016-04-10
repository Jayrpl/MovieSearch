package uk.ac.city.acnh550.searchitunes;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void goToResults(View view) {
        EditText filmTitle = (EditText) findViewById(R.id.film_title);
        String title = filmTitle.getText().toString();

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("movieTitle", title);
        startActivity(intent);
    }
}


/*      I have added an onItemClick method that responds to a result item in the listView on the results activity.
        The String from the "imdbID" part in the json of the search results was filtered and added to an arrayList of type String named titleURL,
        so that it held all of the different imdbIDs.
        When the onItemClick listener is called, the titleURL String corresponding to the item position which has been clicked is added to the end of
        the "www.imdb.com/title/" String. The browser opens and goes to the webpage corresponding to the whole URL string.

        */