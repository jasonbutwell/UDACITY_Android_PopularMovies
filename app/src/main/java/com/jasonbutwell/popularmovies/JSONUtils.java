package com.jasonbutwell.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by J on 22/01/2017.
 */

public final class JSONUtils {

    private JSONUtils() {}

    public static ArrayList<String> extractFromJSONArray(String JSONData) throws JSONException {

        ArrayList<String> movie_posters = new ArrayList<>();

        JSONObject movieData = new JSONObject(JSONData);
        JSONArray movieDataArray = movieData.getJSONArray("results");

        for (int i=0; i< movieDataArray.length(); i++ ) {
            JSONObject movieItem = movieDataArray.getJSONObject(i);

            String poster_path = movieItem.getString("poster_path");
            movie_posters.add(poster_path);
        }
        return movie_posters;
    }
}
