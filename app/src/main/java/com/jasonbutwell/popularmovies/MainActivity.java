package com.jasonbutwell.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView testTV;
    private FrameLayout loadingIndicator;

    private void showSortBy() {
        testTV.setText(TMDBHelper.getFilterQueryString());
    }

    // Set the loading indicator to be visible or invisible
    // Shows and hides a frame layout with 2 child views
    
    private void showLoadingIndicator( boolean show ) {
        if ( show )
            loadingIndicator.setVisibility(View.VISIBLE);
        else
            loadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = (FrameLayout)findViewById(R.id.loadingIndicator);

        // Replace here or in APIKey.java with your own 'TMDB API KEY'
        TMDBHelper.setApiKey( APIKey.get() );

        testTV = (TextView) findViewById(R.id.test);

        TMDBHelper.setSortByText(TMDBHelper.POPULAR);

        loadMovieData();
    }

    private void loadMovieData() {
        new TMDBQueryTask().execute(TMDBHelper.buildBaseURL());
    }

    // Create our options menu so we can filter the movies by
    // 1. Popular, 2. Top rated

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by_menu, menu);
        return true;
    }

    // Detect what filter was requested and set that filter
    // to be used for the http get request

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId() ) {

            case R.id.sortby_popular :
                TMDBHelper.setSortByText(TMDBHelper.POPULAR);
                loadMovieData();
                return true;

            case R.id.sortby_top_rated :
                TMDBHelper.setSortByText(TMDBHelper.TOP_RATED);
                loadMovieData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class TMDBQueryTask extends AsyncTask<URL, Void, ArrayList<String>> {

        URL UrlToSearch = null;
        String searchResults = null;
        String movie_posters[];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Loading Indicator visible
            showLoadingIndicator( true );
        }

        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            URL searchURL = null;
            searchURL = urls[0];

            ArrayList<String> movie_posters = null;

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl( searchURL );
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
               movie_posters = JSONUtils.extractFromJSONArray( searchResults, TMDBHelper.JSON_MOVIE_POSTER );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movie_posters;
        }

        @Override
        protected void onPostExecute(ArrayList<String> movie_posters) {
            //super.onPostExecute(s);
            // Loading indicator invisible
            showLoadingIndicator( false );

            testTV.setText(searchResults);

            for ( String movie_poster_name : movie_posters) {
                testTV.append(TMDBHelper.buildImageURL(movie_poster_name) + "\n");
            }
        }
    }
}
