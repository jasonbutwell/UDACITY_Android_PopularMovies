package com.jasonbutwell.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private FrameLayout loadingIndicator;

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

        // Replace here or in APIKey.java with your own 'TMDB API KEY'
        TMDBHelper.setApiKey( APIKey.get() );

        ArrayList<MovieItem> movie_posters = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movie_posters);

        gridView = (GridView) findViewById(R.id.gridView);
        loadingIndicator = (FrameLayout)findViewById(R.id.loadingIndicator);

        gridView.setAdapter(movieAdapter);
        TMDBHelper.setSortByText(TMDBHelper.POPULAR);

        // Click Listener for the gridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Display a toast message for now. Will override with film details later.
                Toast.makeText(getApplicationContext(),"You clicked on item #"+String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });

        loadMovieData();
    }

    private void updateMovies(ArrayList<MovieItem> arrayList) {
        // Scroll to first item in grid
        gridView.smoothScrollToPosition(0);
        // reset the dataset for the adapter
        movieAdapter.setData(arrayList);
    }

    private void loadMovieData() {
        new TMDBQueryTask().execute( TMDBHelper.buildBaseURL() );
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

    public class TMDBQueryTask extends AsyncTask<URL, Void, ArrayList<MovieItem>> {

        URL UrlToSearch = null;
        String searchResults = null;
        ArrayList<MovieItem> arrayList = null;

        @Override
        protected void onPreExecute() {
            // Loading Indicator visible
            showLoadingIndicator( true );
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(URL... urls) {

            URL searchURL = null;
            searchURL = urls[0];

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl( searchURL );

            } catch (IOException e) {
                e.printStackTrace();
            }

            arrayList = JSONUtils.extractFromJSONArray( searchResults );

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> arrayList) {
            // Loading indicator invisible
            showLoadingIndicator( false );
            updateMovies(arrayList);
        }
    }
}
