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

    public static void extractFromJSONArray(String JSONData, String JSONObjectFieldName, ArrayList<String> arrayList) throws JSONException {

//        ArrayList<String> field_data = new ArrayList<>();
        arrayList.clear();

        String JSONArray_start = "results";

        JSONObject movieData = new JSONObject( JSONData );
        JSONArray movieDataArray = movieData.getJSONArray( JSONArray_start );

        for ( int i=0; i< movieDataArray.length(); i++ ) {
            JSONObject movieItem = movieDataArray.getJSONObject( i );

            String JSONdataItem = movieItem.getString( JSONObjectFieldName );

//            field_data.add( JSONdataItem );
            arrayList.add( TMDBHelper.buildImageURL( JSONdataItem));
        }
        //return field_data;
        //return field_data;
    }
}
