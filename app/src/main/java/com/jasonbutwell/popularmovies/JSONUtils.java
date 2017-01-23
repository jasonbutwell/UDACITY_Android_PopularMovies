package com.jasonbutwell.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by J on 22/01/2017.
 */

final class JSONUtils {

    private JSONUtils() {}

    static ArrayList<String> extractFromJSONArray(String JSONData, String JSONObjectFieldName)  {

        ArrayList<String> data = new ArrayList<>();
        JSONArray movieDataArray = null;
        String JSONArray_start = "results";

        JSONObject movieData = null;
        try {
            movieData = new JSONObject( JSONData );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (movieData != null) {
                movieDataArray = movieData.getJSONArray( JSONArray_start );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (movieDataArray != null) {
            for ( int i=0; i< movieDataArray.length(); i++ ) {
                JSONObject movieItem = null;
                try {
                    movieItem = movieDataArray.getJSONObject( i );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String JSONdataItem = null;
                try {
                    if (movieItem != null) {
                        JSONdataItem = movieItem.getString( JSONObjectFieldName );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                data.add( TMDBHelper.buildImageURL( JSONdataItem ));
            }
        }
        return data;
    }
}
