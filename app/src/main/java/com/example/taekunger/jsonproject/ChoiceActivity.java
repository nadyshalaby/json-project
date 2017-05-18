package com.example.taekunger.jsonproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {

    protected Button all_movies_btn, favorite_movies_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        all_movies_btn = (Button) findViewById(R.id.all_movies_btn);
        favorite_movies_btn = (Button) findViewById(R.id.favorite_movies_btn);

        all_movies_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this, MoviesActivity.class));
            }
        });

        favorite_movies_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this, FavoriteMoviesActivity.class));
            }
        });
    }
}
