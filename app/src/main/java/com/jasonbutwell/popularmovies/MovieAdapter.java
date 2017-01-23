package com.jasonbutwell.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by J on 22/01/2017.
 */

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> movie_posters;

    public MovieAdapter( Context context, ArrayList<String> movie_posters ) {
        this.context = context;
        this.movie_posters = movie_posters;
    }

    public void setData( ArrayList<String> movie_posters ) {
        this.movie_posters = movie_posters;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movie_posters.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        //  Commented out code recycles the view
//        if ( convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_image, parent, false);
//        } else {
//            view = (View) convertView;
//        }

        // Handle the caching of the image with the Picasso library
        Picasso
                .with(context)
                .load( movie_posters.get( position ) )
                .fit()
                .into((ImageView)view);

        return view;
    }
}