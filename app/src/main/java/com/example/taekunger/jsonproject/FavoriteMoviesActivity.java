package com.example.taekunger.jsonproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteMoviesActivity extends AppCompatActivity {

    final protected static String TAG = MoviesActivity.class.getSimpleName();
    protected String url = "http://haladoctor.com/testSec/getMovieDetailWithId.php";
    protected String params = "studentNumber=16&studentSec=5&studentDep=2&mID=";
    protected ProgressDialog progress;
    protected ListView movies_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movies_list = (ListView) findViewById(R.id.movies_list);
        new MoviesTask().execute();

    }

    private class MoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(FavoriteMoviesActivity.this);
            progress.setMessage("Loading...");
            progress.show();
        }


        @Override
        protected ArrayList<Movie> doInBackground(Void... args) {

            ArrayList<Movie> movies = new ArrayList<>();

            Cursor cursor = new DatabaseHelper(FavoriteMoviesActivity.this).getMovies();

            while (cursor.moveToNext()) {

                Movie movie = new Movie();
                Log.e("PARAMS", params + cursor.getInt(0));

                String response = JSONHttpRequest.makeRequest(url + "?" + params + cursor.getInt(1), JSONHttpRequest.GET, null);

                try {
                    JSONObject movieItem = new JSONObject(response);

                    int id = movieItem.getInt("movie_id");
                    String name = movieItem.getString("movie_name");
                    String img = movieItem.getString("movie_img");

                    movie.setId(id);
                    movie.setName(name);
                    movie.setImg(img);

                    movies.add(movie);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return movies;
        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> movies) {
            movies_list.setAdapter(new MovieAdapter(FavoriteMoviesActivity.this, R.layout.movie_item, movies));
            movies_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(FavoriteMoviesActivity.this, MovieActivity.class);
                    i.putExtra("id", movies.get(position).getId());
                    startActivity(i);
                }
            });
            progress.dismiss();
        }

    }
}
