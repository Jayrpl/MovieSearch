package uk.ac.city.acnh550.searchitunes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jaden on 28/11/2015.
 */
public class Film {
    public String title;
    public String year;
    public String poster;
    public String titleURL;


    public Film(JSONObject object){
        try{
            this.title = object.getString("Title");
            this.year = object.getString("Year");
            this.poster = object.getString("Poster");
            this.titleURL = object.getString("imdbID");

        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Film> fromJson(JSONArray jsonArray){
        ArrayList<Film> films = new ArrayList<Film>();
        for (int i = 0; i < jsonArray.length(); i++){
            try{
                films.add(new Film(jsonArray.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return films;
    }




}
