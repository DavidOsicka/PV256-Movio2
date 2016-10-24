package cz.muni.fi.pv256.movio2.uco_396537;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by david on 10.10.16.
 */

public class DetailViewFragment extends Fragment {

    private String movieTitle = "Movie title";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

}
