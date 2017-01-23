package com.jasonbutwell.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by J on 22/01/2017.
 */

final class JSONUtils {

    private JSONUtils() {}

    static String extractJSONString( String JSONData, String extractString ) {
        String JSONString = null;

        try {
            JSONObject json = new JSONObject(JSONData);
            JSONString = json.getString(extractString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return JSONString;
    }

    static ArrayList<MovieItem> extractJSONArray(String JSONData)  {

        ArrayList<MovieItem> data = new ArrayList<>();
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
            for ( int i=0; i < movieDataArray.length(); i++ ) {
                JSONObject movieItem = null;
                try {
                    movieItem = movieDataArray.getJSONObject( i );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String id = null, title = null, posterURL = null, synopsis = null, rating = null, release = null;

                try {
                    if (movieItem != null) {
                        // extract the field strings needed
                        id = movieItem.getString(TMDBHelper.JSON_MOVIE_ID);
                        title = movieItem.getString( TMDBHelper.JSON_MOVIE_TITLE );
                        posterURL = movieItem.getString( TMDBHelper.JSON_MOVIE_POSTER );
                        synopsis = movieItem.getString( TMDBHelper.JSON_MOVIE_OVERVIEW );
                        rating = movieItem.getString( TMDBHelper.JSON_MOVIE_VOTES );
                        release = movieItem.getString( TMDBHelper.JSON_MOVIE_RELEASEDATE );

                        // DEBUG OUTPUT
                        Log.i("MOVIE id:",id);
                        Log.i("MOVIE title:",title);
                        Log.i("MOVIE poster:",posterURL);
                        Log.i("MOVIE plot:",synopsis);
                        Log.i("MOVIE rating:",rating);
                        Log.i("MOVIE release:",release);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MovieItem movie = new MovieItem();
                movie.setId(id);
                movie.setOriginalTitle( title );
                movie.setPosterURL( TMDBHelper.buildImageURL( posterURL ));
                movie.setPlotSynopsis( synopsis );
                movie.setUserRating( rating );
                movie.setReleaseDate( release );
                data.add( movie );
            }
        }
        return data;
    }
}
