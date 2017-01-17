package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by david on 6.1.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) { super(context, "movie.db", null, 1);}

    public void onCreate (SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + DatabaseContract.MovieEntry.TABLE_NAME + " (" +
             //DatabaseContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
             DatabaseContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
             DatabaseContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
             DatabaseContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT," +
             DatabaseContract.MovieEntry.COLUMN_COVER_PATH + " TEXT," +
             DatabaseContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT," +
             DatabaseContract.MovieEntry.COLUMN_POPULARITY + " REAL," +
             DatabaseContract.MovieEntry.COLUMN_OVERVIEW + " TEXT," +
             "UNIQUE (" + DatabaseContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE" + " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
