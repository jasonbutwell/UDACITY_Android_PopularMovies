package com.jasonbutwell.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView movieTitleView = (TextView)findViewById(R.id.movieTitle);
        ImageView moviePosterURLView = (ImageView) findViewById(R.id.moviePoster);
        TextView movieSynopsisView = (TextView) findViewById(R.id.movieDescription);
        TextView movieRatingView = (TextView) findViewById(R.id.movieRating);
        TextView movieReleaseView = (TextView) findViewById(R.id.movieReleaseDate);

        Intent movieDetailsIntent = getIntent();

        String movieTitle = movieDetailsIntent.getStringExtra(TMDBHelper.JSON_MOVIE_TITLE);
        String moviePoster = movieDetailsIntent.getStringExtra(TMDBHelper.JSON_MOVIE_POSTER);
        String movieSynopsis = movieDetailsIntent.getStringExtra(TMDBHelper.JSON_MOVIE_OVERVIEW);
        String movieRating = movieDetailsIntent.getStringExtra(TMDBHelper.JSON_MOVIE_VOTES);
        String movieRelease = movieDetailsIntent.getStringExtra(TMDBHelper.JSON_MOVIE_RELEASEDATE);

        if ( movieTitle != null )
            movieTitleView.setText(movieTitle);

        if ( moviePoster != null ) {
            Picasso
                    .with(getApplicationContext())
                    .load( moviePoster )
                    .fit()
                    .into((ImageView)moviePosterURLView);
        }

        if ( movieSynopsis != null ) {
            movieSynopsisView.setText(movieSynopsis);
        }

        if ( movieRating != null ) {
            movieRatingView.setText(movieRating);
        }

        if ( movieRelease != null ) {
            movieReleaseView.setText(movieRelease);
        }

    }
}
