package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 6.1.17.
 */

public class MovieFindLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private DatabaseManager mDatabaseManager;
    private long mId;

    public MovieFindLoader(Context context, Long id) {
        super(context);
        mDatabaseManager = new DatabaseManager(context);
        mId = id;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mId != 0) {
            return mDatabaseManager.getMovieById(mId);
        }
        return new ArrayList<>();
    }
}
