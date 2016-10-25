package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by david on 10.10.16.
 */

public class DetailViewFragment extends Fragment {

    private static final String TAG = DetailViewFragment.class.getName();
    private static final String ARG_TITLE = "title";

    private String movieTitle = "Movie title";



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, " onAttach method");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " onCreate method");

        if(savedInstanceState != null) {
            Movie movie = savedInstanceState.getParcelable("Movie");
            if(movie != null) {
                movieTitle = movie.getTitle();
            } else {
                movieTitle = savedInstanceState.getString(  ARG_TITLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " onCreateView method");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail_view_fragment, container, false);
        TextView titleView = (TextView)view.findViewById(R.id.movieTitle);

        if(titleView != null) {
            titleView.setText(movieTitle);
        }
        return view;
    }

    public static DetailViewFragment newInstance(Bundle args){
        DetailViewFragment fragment = new DetailViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        //super.setArguments(args);
        if(args != null) {
            Movie movie = args.getParcelable("Movie");
            movieTitle = movie.getTitle();
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_TITLE, movieTitle);
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
