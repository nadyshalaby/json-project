package com.example.taekunger.jsonproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/18/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    protected final static String DB_NAME = "json_db";
    protected final static int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE movies(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " movie_id INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE movies");
    }

    public void addMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put("movie_id", id);

        db.insert("movies", null, data);
    }

    public Cursor getMovies() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(true, "movies", null, null, null, null, null, null, null);
    }

    public Boolean hasMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(true, "movies", null, "movie_id = ?", new String[]{"" + id}, null, null, null, null).moveToNext();
    }


}
