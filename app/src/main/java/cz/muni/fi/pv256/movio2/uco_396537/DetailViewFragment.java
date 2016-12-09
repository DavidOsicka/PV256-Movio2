package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 10.10.16.
 */

public class DetailViewFragment extends Fragment {

    private static final String TAG = DetailViewFragment.class.getName();
    private static final String ARG_TITLE_YEAR = "title_year";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_COVER = "cover";
    private static final String ARG_BACKDROP = "backdrop";

    private String movieTitleYear = "";
    private String movieDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private String coverPicture = "";
    private String backdropPicture = "";


    public static DetailViewFragment newInstance(Bundle args){
        DetailViewFragment fragment = new DetailViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
            movieTitleYear = savedInstanceState.getString(ARG_TITLE_YEAR);
            movieDescription = savedInstanceState.getString(ARG_DESCRIPTION);
            coverPicture = savedInstanceState.getString(ARG_COVER);
            backdropPicture = savedInstanceState.getString(ARG_BACKDROP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " onCreateView method");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail_view_fragment, container, false);

        TextView titleView = (TextView)view.findViewById(R.id.movie_title_year);
        TextView descriptionView = (TextView)view.findViewById(R.id.movie_description);
        ImageView coverView = (ImageView)view.findViewById(R.id.image_cover);
        ImageView backdropView = (ImageView)view.findViewById(R.id.image_backdrop);

        if(titleView != null) {
            titleView.setText(movieTitleYear);
        }
        if(descriptionView != null) {
            descriptionView.setText(movieDescription);
        }
        if(coverView != null && !coverPicture.isEmpty()) {
            coverView.setImageBitmap(Model.getInstance().getPicture(coverPicture));
        }
        if(backdropView != null && !backdropPicture.isEmpty()) {
            backdropView.setImageBitmap(Model.getInstance().getPicture(backdropPicture));
        }
        return view;
    }

    @Override
    public void setArguments(Bundle args) {
        //super.setArguments(args);
        if(args != null && args.containsKey("Movie")) {
            Movie movie = args.getParcelable("Movie");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(movie.getReleaseDate());
            movieTitleYear = movie.getTitle() + System.lineSeparator() + String.valueOf(calendar.get(Calendar.YEAR));
            coverPicture = movie.getCoverPath();
            backdropPicture = movie.getBackdrop();
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        outState.putString(ARG_TITLE_YEAR, movieTitleYear);
        outState.putString(ARG_DESCRIPTION, movieDescription);
        outState.putString(ARG_COVER, coverPicture);
        outState.putString(ARG_BACKDROP, backdropPicture);
        super.onSaveInstanceState(outState);
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
