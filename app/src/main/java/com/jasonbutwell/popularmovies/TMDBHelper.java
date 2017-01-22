package com.jasonbutwell.popularmovies;

/**
 * Created by J on 21/01/2017.
 */

public final class TMDBHelper {
    // Private constructor
    private TMDBHelper() {}

    public static final int POPULAR=0, TOP_RATED=1;
    private static int sortByFilter = POPULAR;

    private static final String[] queryFilters = { "popular", "top_rated" };
    private static String filterQuery = "";

    public static String getFilterQueryString() {
        return filterQuery;
    }

    public static void setSortByFilter( int id ) {
        sortByFilter = id;
    }

    public static void setSortByText( int id ) {
        filterQuery = getSortByText(id);
    }

    public static String getSortByText( int id ) {
        if ( id < queryFilters.length )
            return queryFilters[id];
        else
            return "";
    }
}
