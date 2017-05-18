package com.example.taekunger.jsonproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Taekunger on 5/18/2017.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    protected ArrayList<Movie> movies;
    protected int resource;
    protected Context context;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> movies) {
        super(context, resource, movies);

        this.movies = movies;
        this.resource = resource;
        this.context = context;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(resource, parent, false);

        ImageView img = (ImageView) view.findViewById(R.id.movie_img);
        TextView name = (TextView) view.findViewById(R.id.movie_name);
        Movie movie = movies.get(position);

        name.setText(movie.getName());
        Picasso.with(context).load(movie.getImg()).into(img);

        return view;
    }
}
