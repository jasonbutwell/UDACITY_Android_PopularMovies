package com.jasonbutwell.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView testTV;

    private void showSortBy() {
        testTV.setText(TMDBHelper.getFilterQueryString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Replace here or in APIKey.java with your own 'TMDB API KEY'
        TMDBHelper.setApiKey( APIKey.get() );

        testTV = (TextView) findViewById(R.id.test);

        TMDBHelper.setSortByText(TMDBHelper.POPULAR);

        Log.d("TMDB",TMDBHelper.buildBaseURL());

        testTV.setText(TMDBHelper.buildBaseURL());
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
                testTV.setText(TMDBHelper.buildBaseURL());
                return true;

            case R.id.sortby_top_rated :
                TMDBHelper.setSortByText(TMDBHelper.TOP_RATED);
                testTV.setText(TMDBHelper.buildBaseURL());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
