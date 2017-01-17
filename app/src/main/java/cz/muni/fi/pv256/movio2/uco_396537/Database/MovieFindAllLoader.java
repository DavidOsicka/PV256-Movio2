package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 6.1.17.
 */

public class MovieFindAllLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private DatabaseManager mDatabaseManager;

    public MovieFindAllLoader(Context context) {
        super(context);
        mDatabaseManager = new DatabaseManager(context);
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        return mDatabaseManager.getAllMovies();
    }
}
