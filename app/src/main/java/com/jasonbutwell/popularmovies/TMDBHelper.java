package com.jasonbutwell.popularmovies;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by J on 21/01/2017.
 */

 final class TMDBHelper {
    private TMDBHelper() {}     // Private constructor

    private static String API_KEY;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    private static final String PARAM_SORTBY = "sort_by";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_PAGE = "page";

    static final int POPULAR = 0, TOP_RATED = 1;
    private static final String[] queryFilters = { "popular", "top_rated" };
    private static String filterQuery = queryFilters[POPULAR];
    private static int page_number = 1;

    // Quick way to build an Image URL to fetch image extracted from JSON
    static String buildImageURL(String imageName) {
        return (BASE_IMAGE_URL+IMAGE_SIZE+imageName);
    }

    // Builds the base URL to retrieve the JSON
    static URL buildBaseURL() {
        URL url = null;

        Uri.Builder buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_PAGE, getPage_number_string())
                //.appendQueryParameter(PARAM_SORTBY,filterQuery)
                .appendPath(filterQuery)
                .appendQueryParameter(PARAM_API_KEY, API_KEY);

        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // Allow us to store API KEY here
    static void setApiKey( String ApiKey ) {
        API_KEY = ApiKey;
    }

    // Increase current page
    public static void nextPage() {
        page_number++;
    }

    // Set page number we are on
    public static void setPageNumber( int pagenumber ) {
        page_number = pagenumber;
    }

    // Gets the current page number
    public static int getPage_number() {
        return page_number;
    }

    // Get the page number as a string
    private static String getPage_number_string() {
        return String.valueOf(page_number);
    }

    // Get the filter query component
    static String getFilterQueryString() {
        return filterQuery;
    }

    // Set filter query component
    static void setSortByText(int id) {
        filterQuery = getSortByText(id);
    }

    // Get the sort query component as a string
    private static String getSortByText(int id) {
        if ( id < queryFilters.length )
            return queryFilters[id];
        else
            return "";
    }
}
