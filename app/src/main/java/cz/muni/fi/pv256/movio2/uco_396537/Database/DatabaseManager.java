package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 6.1.17.
 */

public class DatabaseManager {

    private Context mContext;

    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_RELEASE_DATE = 2;
    public static final int COLUMN_COVER_PATH = 3;
    public static final int COLUMN_BACKDROP_PATH = 4;
    public static final int COLUMN_POPULARITY = 5;
    public static final int COLUMN_OVERVIEW = 6;

    private static final String[] MOVIE_COLUMNS = {
            DatabaseContract.MovieEntry.COLUMN_MOVIE_ID,
            DatabaseContract.MovieEntry.COLUMN_TITLE,
            DatabaseContract.MovieEntry.COLUMN_RELEASE_DATE,
            DatabaseContract.MovieEntry.COLUMN_COVER_PATH,
            DatabaseContract.MovieEntry.COLUMN_BACKDROP_PATH,
            DatabaseContract.MovieEntry.COLUMN_POPULARITY,
            DatabaseContract.MovieEntry.COLUMN_OVERVIEW
    };

    private static final String WHERE_MOVIE_ID = DatabaseContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";


    public DatabaseManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == 0) {
            throw new IllegalStateException("movie id should not be 0");
        }
        if (movie.getTitle() == null) {
            throw new IllegalStateException("movie title cannot be null");
        }

        ContentUris.parseId(mContext.getContentResolver().insert(DatabaseContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie)));
        //movie.setId(ContentUris.parseId(mContext.getContentResolver().insert(DatabaseContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie))));
    }

    public ArrayList<Movie> getAllMovies() {
        Cursor cursor = mContext.getContentResolver().query(DatabaseContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, "", new String[]{}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }
        if (cursor != null) {
            cursor.close();
        }
        return new ArrayList<>();
    }

    public ArrayList<Movie> getMovieById(long id) {
        if (id == 0) {
            throw new IllegalStateException("id is 0");
        }

        Cursor cursor = mContext.getContentResolver().query(DatabaseContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_MOVIE_ID, new String[]{new Long(id).toString()}, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }
        if (cursor != null) {
            cursor.close();
        }
        return new ArrayList<>();
    }

    public void updateMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == 0) {
            throw new IllegalStateException("movie id cannot be 0");
        }
        if (movie.getTitle() == null) {
            throw new IllegalStateException("movie title cannot be null");
        }
        mContext.getContentResolver().update(DatabaseContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie), WHERE_MOVIE_ID, new String[]{String.valueOf(movie.getId())});
    }

    public void deleteMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == 0) {
            throw new IllegalStateException("movie id cannot be 0");
        }
        mContext.getContentResolver().delete(DatabaseContract.MovieEntry.CONTENT_URI, WHERE_MOVIE_ID, new String[]{String.valueOf(movie.getId())});
    }

    private ContentValues prepareMovieValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(DatabaseContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(DatabaseContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(DatabaseContract.MovieEntry.COLUMN_COVER_PATH, movie.getCover());
        values.put(DatabaseContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop());
        values.put(DatabaseContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(DatabaseContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        return values;
    }

    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getLong(COLUMN_MOVIE_ID));
        movie.setTitle(cursor.getString(COLUMN_TITLE));
        movie.setReleaseDate(cursor.getString(COLUMN_RELEASE_DATE));
        movie.setCover(cursor.getString(COLUMN_COVER_PATH));
        movie.setBackdrop(cursor.getString(COLUMN_BACKDROP_PATH));
        movie.setPopularity(cursor.getFloat(COLUMN_POPULARITY));
        movie.setOverview(cursor.getString(COLUMN_OVERVIEW));
        return movie;
    }
}
