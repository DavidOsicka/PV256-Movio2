package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import cz.muni.fi.pv256.movio2.uco_396537.Database.MovieFindAllLoader;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 10.10.16.
 */

public class ListViewFragment extends Fragment {

    private static final String TAG = ListViewFragment.class.getName();

    private static final int LOADER_FIND_ALL = 4;
    private static final String ARG_SHOW_SAVED = "show_saved";

    private RecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private Context mContext = null;
    private ArrayList<Object> mSavedMovies = new ArrayList<>();
    private boolean mDownloading = false;
    private boolean mShowSavedMovies = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, " onAttach  method");
        mContext = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " onCreate  method");

        if(savedInstanceState != null) {
            mShowSavedMovies = savedInstanceState.getBoolean(ARG_SHOW_SAVED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " onCreateView method ");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        if(mRecyclerView != null) {
            // this setting improves performance if you know that changes in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        Model.getInstance().setContext(this);
        if(mShowSavedMovies) {
                loadSavedMovies();
//            setData();
//            getLoaderManager().initLoader(LOADER_FIND_ALL, new Bundle(), new MovieCallback(getActivity().getApplicationContext())).forceLoad();
        } else {
            reloadData();
//            setData(Model.getInstance().getMovies());
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Log.d(TAG, " onStart method");
    }
    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        Log.d(TAG, " onResume method");
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        Log.d(TAG, " onPause method");
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        Log.d(TAG, " onStop method");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        Log.d(TAG, " onDestroy method");
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        outState.putBoolean(ARG_SHOW_SAVED, mShowSavedMovies);
        super.onSaveInstanceState(outState);
    }

    public void loadSavedMovies() {
        getLoaderManager().initLoader(LOADER_FIND_ALL, new Bundle(), new MovieCallback(getActivity().getApplicationContext())).forceLoad();
    }

    public void setDownloading(boolean downloading) {
        mDownloading = downloading;
        reloadData();
    }

    public void setShowSavedMovies(boolean showSavedMovies) {
        mShowSavedMovies = showSavedMovies;
        reloadData();
    }

    public void reloadData() {
        if(mRecyclerView == null) {
            return;
        }
        if(mDownloading) {
            mAdapter = new RecyclerViewAdapter("Downloading data");
            mRecyclerView.setAdapter(mAdapter);
        } else if(mShowSavedMovies) {
            if(mSavedMovies.isEmpty()) {
                mAdapter = new RecyclerViewAdapter("No movies saved in database");
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter = new RecyclerViewAdapter(mSavedMovies, mContext);
                mRecyclerView.setAdapter(mAdapter);
            }
        } else {
            if(Model.getInstance().getMovies().isEmpty()) {
                mAdapter = new RecyclerViewAdapter("Sorry, no data available");
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter = new RecyclerViewAdapter(Model.getInstance().getMovies(), mContext);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }


    public class MovieCallback implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
        Context mContext;

        public MovieCallback(Context context) {
            mContext = context;
        }

        @Override
        public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
            Log.i(TAG, " onCreateLoader method");
            switch (id) {
                case LOADER_FIND_ALL:
                    Log.i(TAG, " LOADER_FIND_ALL");
                    return new MovieFindAllLoader(mContext);
                default:
                    throw new UnsupportedOperationException("Not know loader id");
            }
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
            Log.i(TAG, " onLoadFinished method");
            switch (loader.getId()) {
                case LOADER_FIND_ALL:
                    Log.i(TAG, " LOADER_FIND_ALL " + data.size());
                    mSavedMovies.clear();
                    mSavedMovies.addAll(data);
                    reloadData();
//                    ArrayList<Object> myData = new ArrayList<>();
//                    myData.addAll(data);
//                    setData(myData);
                    break;
                default:
                    throw new UnsupportedOperationException("Not know loader id");
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
            Log.i(TAG, " onLoadReset method");
        }
    }
}
