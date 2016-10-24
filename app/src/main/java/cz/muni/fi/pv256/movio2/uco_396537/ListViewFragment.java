package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by david on 10.10.16.
 */

public class ListViewFragment extends Fragment {

    private RecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager = null;

    private WeakReference<Context> mContextWeakReference;

    private String[] dataset = {"movie 1", "movie 2", "movie 3"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        if(mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        // specify an adapter (see also next example)
        if(mContextWeakReference != null) {
            mAdapter = new RecyclerViewAdapter(dataset, mContextWeakReference.get());
            mRecyclerView.setAdapter(mAdapter);
        }

        return view;
    }

    public void setAppContext(Context context) {
        if(context != null) {
            mContextWeakReference = new WeakReference<Context>(context);
        }
    }
}
