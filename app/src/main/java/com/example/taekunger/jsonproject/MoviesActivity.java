package com.example.taekunger.jsonproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {

    final protected static String TAG = MoviesActivity.class.getSimpleName();
    protected String url = "http://haladoctor.com/testSec/getAllMovies.php";
    protected String params = "studentNumber=16&studentSec=5&studentDep=2";
    protected ProgressDialog progress;
    protected ListView movies_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movies_list = (ListView) findViewById(R.id.movies_list);
        new MoviesTask().execute();

    }

    private class MoviesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MoviesActivity.this);
            progress.setMessage("Loading...");
            progress.show();
        }


        @Override
        protected String doInBackground(Void... args) {
            String response = JSONHttpRequest.makeRequest(url + "?" + params, JSONHttpRequest.GET, null);
            Log.i(LoginActivity.TAG, params + "Response: " + response);
            return response;

        }

        @Override
        protected void onPostExecute(String response) {
            try {

                Log.e(TAG, "Response: " + response);

                JSONArray movies = new JSONArray(response);
                final ArrayList<Movie> moviesList = new ArrayList<>();

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);

                    int id = movie.getInt("movie_id_160");
                    String name = movie.getString("movie_name_160");
                    String img = movie.getString("movie_img_160");

                    Movie movieItem = new Movie();
                    movieItem.setId(id);
                    movieItem.setName(name);
                    movieItem.setImg(img);

                    moviesList.add(movieItem);
                }

                movies_list.setAdapter(new MovieAdapter(MoviesActivity.this, R.layout.movie_item, moviesList));
                movies_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MoviesActivity.this, MovieActivity.class);
                        i.putExtra("id", moviesList.get(position).getId());
                        startActivity(i);
                    }
                });

            } catch (Exception e) {
                Log.e(LoginActivity.TAG, "Exception: " + e.getMessage());
            } finally {
                progress.dismiss();
            }
        }

    }
}
