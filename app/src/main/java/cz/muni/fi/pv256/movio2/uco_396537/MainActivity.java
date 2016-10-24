package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "pref";
    private static final String THEME_NAME = "theme";

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private boolean isTablet = false;
    private ArrayList<Movie> mMovies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.

        // This starts a class with strict mode
        App app = new App();
        app.onCreate();

        // If we're being restored from a previous state, then we don't need to do anything and should return or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // This is used to reload theme from preferences when swithing themes
        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            setTheme(R.style.AppTheme1);
        } else {
            setTheme(R.style.AppTheme2);
        }

        ListViewFragment listViewFragment = new ListViewFragment();
        DetailViewFragment detailViewFragment = new DetailViewFragment();
        listViewFragment.setAppContext(this);

        isTablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;

        mMovies.add(new Movie("Film 1", 1991, "", "", 0.0f));
        mMovies.add(new Movie("Film 2", 1992, "", "", 0.0f));
        mMovies.add(new Movie("Film 3", 1993, "", "", 0.0f));

        if(isTablet) {
            //code for big screen (like tablet)
            setContentView(R.layout.activity_main_large);
        }else{
            //code for small screen (like smartphone)
            setContentView(R.layout.activity_main);
        }

        if(findViewById(R.id.main_fragment_container) != null) {
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
        }
        if(findViewById(R.id.detail_fragment_container) != null) {
            fragmentManager.beginTransaction().add(R.id.detail_fragment_container, detailViewFragment).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.

    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }


    public void onMovieItemSelected(int item) {
        if(item < 0 || item > mMovies.size()) {
            return;
        }
        Intent intent = new Intent(this, DetailViewFragment.class);
        intent.putExtra("Movie", mMovies.get(item));
        Bundle bundle = intent.getExtras();

        DetailViewFragment detailViewFragment = DetailViewFragment.newInstance(bundle);
        FragmentTransaction transition = fragmentManager.beginTransaction();

        if(isTablet) {
            //code for big screen (like tablet)
            if(findViewById(R.id.detail_fragment_container) != null) {
                transition.replace(R.id.detail_fragment_container, detailViewFragment);
            }
        }else{
            //code for small screen (like smartphone)
            if(findViewById(R.id.main_fragment_container) != null) {
                transition.replace(R.id.main_fragment_container, detailViewFragment);
            }
        }
        transition.addToBackStack("detail_fragment");
        transition.commit();
    }


    public void backToList(View view) {
        if(isTablet) {
            //code for big screen (like tablet)
        }else{
            //code for small screen (like smartphone)
            if(findViewById(R.id.main_fragment_container) != null) {
                fragmentManager.popBackStack("detail_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //fragmentManager.beginTransaction().replace(R.id.main_fragment_container, listViewFragment).commit();
            }
        }
    }


    public void changeTheme(View view) {
        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit();

        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            editor.putString(THEME_NAME, "AppTheme2");
        } else {
            editor.putString(THEME_NAME, "AppTheme1");
        }

        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
}
