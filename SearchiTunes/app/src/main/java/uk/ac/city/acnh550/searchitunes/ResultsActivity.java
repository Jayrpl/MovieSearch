package uk.ac.city.acnh550.searchitunes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ResultsActivity extends AppCompatActivity {

   public ArrayList<String> titleURL = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String keyword = getIntent().getExtras().getString("movieTitle");
        keyword = keyword.replaceAll("\\s", "+");

        FilmDatabase db = new FilmDatabase(this);


        String jsonResult = db.getResult(keyword);

        if (jsonResult == null){
            jsonResult = runSearch(keyword);
            db.insertData(keyword, jsonResult);
        }
        ArrayList<String> searchResults = getValuesFromJSON(jsonResult);

        final ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, searchResults);

        listView.setAdapter(arrayAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getAdapter().getItem(position);
                Uri uri = Uri.parse("http://www.imdb.com/title/" + titleURL.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });



        final Button Back = (Button) findViewById(R.id.Back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ResultsActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        }





    private String runSearch(String keyword){
        String jsonResult = null;

        try {
            URL url = new URL("https://www.omdbapi.com/?s=" + keyword);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream input = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(input, "UTF-8").useDelimiter("\\A");
            jsonResult = scanner.hasNext() ? scanner.next() : "";

        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }


    private ArrayList<String> getValuesFromJSON(String jsonResult){
        ArrayList<String> results = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject result = jsonArray.getJSONObject(i);

                StringBuilder sb = new StringBuilder();
                sb.append(result.getString("Title"));
               sb.append(" - ");
               sb.append(result.getString("Year"));
               results.add(sb.toString());
               StringBuilder sbv = new StringBuilder();
                sbv.append(result.getString("imdbID"));
               titleURL.add(sbv.toString());

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
}
