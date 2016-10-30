package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;

/**
 * Created by david on 10.10.16.
 */

public class ListViewFragment extends Fragment {

    private static final String TAG = ListViewFragment.class.getName();

    private RecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager = null;

    private WeakReference<Context> mContextWeakReference = null;

    private String[] dataset = {"movie 1", "movie 2", "movie 3"};


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, " onAttach  method");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " onCreate  method");
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

        if(Model.ITEMS.isEmpty()) {
            mAdapter = new RecyclerViewAdapter("Sorry, no data available");
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new RecyclerViewAdapter(Model.ITEMS, mContextWeakReference.get());
            mRecyclerView.setAdapter(mAdapter);
        }



        return view;
    }

    public void setAppContext(Context context) {
        if(context != null) {
            mContextWeakReference = new WeakReference<Context>(context);
        }
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
}
