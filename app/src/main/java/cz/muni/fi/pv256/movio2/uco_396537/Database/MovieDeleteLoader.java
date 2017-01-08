package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 6.1.17.
 */

public class MovieDeleteLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private DatabaseManager mDatabaseManager;
    private Movie mMovie;

    public MovieDeleteLoader(Context context, Movie movie) {
        super(context);
        mDatabaseManager = new DatabaseManager(context);
        mMovie = movie;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        mDatabaseManager.deleteMovie(mMovie);
        return new ArrayList<>();
    }
}
