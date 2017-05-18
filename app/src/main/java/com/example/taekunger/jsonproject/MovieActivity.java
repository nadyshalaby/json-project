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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    final protected static String TAG = MovieActivity.class.getSimpleName();
    protected ImageView movie_img;
    protected TextView movie_lbl;
    protected Button favorite_btn;
    protected String url = "http://haladoctor.com/testSec/getMovieDetailWithId.php";
    protected String params = "studentNumber=16&studentSec=5&studentDep=2&mID=";
    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movie_img = (ImageView) findViewById(R.id.movie_img);
        movie_lbl = (TextView) findViewById(R.id.movie_lbl);
        favorite_btn = (Button) findViewById(R.id.favorite_btn);

        Intent i = getIntent();
        final int id = i.getIntExtra("id", 0);
        params += id;

        new MovieTask().execute();

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(MovieActivity.this);
                if (!db.hasMovie(id)) {
                    db.addMovie(id);
                    Toast.makeText(MovieActivity.this, "Movie Added To Your Favorite List.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieActivity.this, "Movie Already Exists in Your Favorite List.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MovieTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MovieActivity.this);
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

                JSONObject movie = new JSONObject(response);

                int id = movie.getInt("movie_id");
                String name = movie.getString("movie_name");
                String img = movie.getString("movie_img");

                movie_lbl.setText(name);
                Picasso.with(MovieActivity.this).load(img).into(movie_img);

            } catch (Exception e) {
                Log.e(LoginActivity.TAG, "Exception: " + e.getMessage());
            } finally {
                progress.dismiss();
            }
        }

    }
}
