package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.Database.DatabaseManager;
import cz.muni.fi.pv256.movio2.uco_396537.Database.MovieCreateLoader;
import cz.muni.fi.pv256.movio2.uco_396537.Database.MovieDeleteLoader;
import cz.muni.fi.pv256.movio2.uco_396537.Database.MovieFindAllLoader;
import cz.muni.fi.pv256.movio2.uco_396537.Database.MovieFindLoader;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 10.10.16.
 */

public class DetailViewFragment extends Fragment {

    private static final String TAG = DetailViewFragment.class.getName();

    public static final String ARG_MOVIE = "movie";
    public static final String CURRENT_MOVIE_ID = "id";
    public static final String CURRENT_MOVIE = "movie";
    private static final int LOADER_FIND_MOVIE = 1;
    private static final int LOADER_CREATE_MOVIE = 2;
    private static final int LOADER_DELETE_MOVIE = 3;
    private static final int LOADER_FIND_ALL = 4;

    private static DetailViewFragment sInstance = null;
    private Movie mMovie = null;
    private DatabaseManager mDatabaseManager = null;
//    private WeakReference<MainActivity> mMainActivity = null;


    public static DetailViewFragment newInstance(Bundle args){
        DetailViewFragment fragment = new DetailViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailViewFragment getInstace() { // kvuli možnost ziskat view v onReciev
        return sInstance;
    }

//    public void setContext(MainActivity mainActivity) {
//        mMainActivity = new WeakReference<>(mainActivity);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onAttach method");
        }
        sInstance = DetailViewFragment.this;
        mDatabaseManager = new DatabaseManager(getActivity().getApplicationContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onCreate method");
        }
        sInstance = DetailViewFragment.this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onCreateView method");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail_view_fragment, container, false);

        TextView titleView = (TextView)view.findViewById(R.id.movie_title_year);
        TextView descriptionView = (TextView)view.findViewById(R.id.movie_description);
        ImageView coverView = (ImageView)view.findViewById(R.id.image_cover);
        ImageView backdropView = (ImageView)view.findViewById(R.id.image_backdrop);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        if(mMovie != null) {
            if (titleView != null) {
                titleView.setText(mMovie.getTitle() + System.lineSeparator() + mMovie.getReleaseDate()); //+ System.lineSeparator() + String.valueOf(calendar.get(Calendar.YEAR));
            }
            if (descriptionView != null) {
                descriptionView.setText(mMovie.getOverview());
            }
            if (coverView != null) {
                coverView.setImageBitmap(Model.getInstance().getPicture(mMovie.getCover()));
            } else {
                coverView.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            }
            if (backdropView != null) {
                backdropView.setImageBitmap(Model.getInstance().getPicture(mMovie.getBackdrop()));
            } else {
                backdropView.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            }
            if (mDatabaseManager.getMovieById(mMovie.getId()).size() == 0) {
                fab.setImageResource(android.R.drawable.ic_input_add);
            } else {
                fab.setImageResource(android.R.drawable.ic_menu_delete);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putLong(CURRENT_MOVIE_ID, mMovie.getId());
                    args.putParcelable(CURRENT_MOVIE, mMovie);

                    if (mDatabaseManager.getMovieById(mMovie.getId()).size() == 0) {
                        getLoaderManager().initLoader(LOADER_CREATE_MOVIE, args, new MovieCallback(getActivity().getApplicationContext())).forceLoad();
                    } else {
                        getLoaderManager().initLoader(LOADER_DELETE_MOVIE, args, new MovieCallback(getActivity().getApplicationContext())).forceLoad();
                    }
                    getLoaderManager().initLoader(LOADER_FIND_ALL, args, new MovieCallback(getActivity().getApplicationContext())).forceLoad();
                }
            });
        }

        return view;
    }

    @Override
    public void setArguments(Bundle args) {
        if(args != null && args.containsKey(ARG_MOVIE)) {
            mMovie = args.getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        outState.putParcelable(ARG_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onStart method");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onResume method");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onPause method");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onStop method");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onDestroy method");
        }
    }


//    public void runLoader(final int loaderType) {
//        Bundle args = new Bundle();
//        if(mMovie != null) {
//            args.putLong(CURRENT_MOVIE_ID, mMovie.getId());
//            args.putParcelable(CURRENT_MOVIE, mMovie);
//        }
//        getLoaderManager().initLoader(loaderType, args, new MovieCallback(getActivity().getApplicationContext())).forceLoad();
//    }


    public class MovieCallback implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
        Context mContext;

        public MovieCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, " onCreateLoader method");
            }
            switch (id) {
                case LOADER_FIND_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_FIND_MOVIE");
                    }
                    return new MovieFindLoader(mContext, args.getLong(CURRENT_MOVIE_ID, 0));
                case LOADER_CREATE_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_CREATE_MOVIE");
                    }
                    return new MovieCreateLoader(mContext, (Movie) args.getParcelable(CURRENT_MOVIE));
                case LOADER_DELETE_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_DELETE_MOVIE");
                    }
                    return new MovieDeleteLoader(mContext, (Movie) args.getParcelable(CURRENT_MOVIE));
                case LOADER_FIND_ALL:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_FIND_ALL");
                    }
                    return new MovieFindAllLoader(mContext);
                default:
                    throw new UnsupportedOperationException("Not know loader id");
            }
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, " onLoadFinished method");
            }
            switch (loader.getId()) {
                case LOADER_FIND_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_FIND_MOVIE");
                    }
                    Bundle args = new Bundle();
                    args.putLong(CURRENT_MOVIE_ID, mMovie.getId());
                    args.putParcelable(CURRENT_MOVIE, mMovie);

//                    if (data.size() == 0) {
//                        getLoaderManager().initLoader(LOADER_CREATE_MOVIE, args, MovieCallback.this).forceLoad();
//                        getLoaderManager().initLoader(LOADER_FIND_ALL, args, MovieCallback.this).forceLoad();
//                    } else {
//                        getLoaderManager().initLoader(LOADER_DELETE_MOVIE, args, MovieCallback.this).forceLoad();
//                        getLoaderManager().initLoader(LOADER_FIND_ALL, args, MovieCallback.this).forceLoad();
//                    }
                    break;
                case LOADER_CREATE_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_CREATE_MOVIE");
                    }
                    if (DetailViewFragment.getInstace() != null) {
                        View view = DetailViewFragment.getInstace().getView();
                        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
                        fab.setImageResource(android.R.drawable.ic_menu_delete);
                    }
                    break;
                case LOADER_DELETE_MOVIE:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_DELETE_MOVIE");
                    }
                    if (DetailViewFragment.getInstace() != null) {
                        View view = DetailViewFragment.getInstace().getView();
                        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
                        fab.setImageResource(android.R.drawable.ic_input_add);
                    }
                    break;
                case LOADER_FIND_ALL:
                    if (BuildConfig.DEBUG) {
                        Log.i(TAG, " LOADER_FIND_ALL " + data.size());
                    }
//                    if(mMainActivity != null) {
//                        mMainActivity.get().mSavedMovies.clear();
//                        mMainActivity.get().mSavedMovies.addAll(data);
//                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Not know loader id");
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, " onLoadReset method");
            }
        }
    }
}
