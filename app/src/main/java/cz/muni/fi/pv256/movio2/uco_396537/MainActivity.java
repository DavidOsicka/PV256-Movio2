package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "pref";
    private static final String THEME_NAME = "theme";

    private final ListViewFragment listViewFragment = new ListViewFragment();
    private final DetailViewFragment detailViewFragment = new DetailViewFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This starts a class with strict mode
        App app = new App();
        app.onCreate();

        super.onCreate(savedInstanceState);

        // This is used to reload theme from preferences when swithing themes
        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            setTheme(R.style.AppTheme1);
        } else {
            setTheme(R.style.AppTheme2);
        }

        // If we're being restored from a previous state, then we don't need to do anything and should return or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //code for big screen (like tablet)
            setContentView(R.layout.activity_main_large);
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
            fragmentManager.beginTransaction().add(R.id.detail_fragment_container, detailViewFragment).commit();

        }else{
            //code for small screen (like smartphone)
            setContentView(R.layout.activity_main);
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
        }



        /*
        // Check that the activity is using the layout version with the fragment_container FrameLayout
        if (findViewById(R.id.main_fragment_container) != null && findViewById(R.id.detail_fragment_container) != null) {

            // However, if we're being restored from a previous state, then we don't need to do anything and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create new Fragments
            ListViewFragment listViewFragment = new ListViewFragment();
            DetailViewFragment detailViewFragment = new DetailViewFragment();

            // Check the display size
            //DisplayMetrics metrics = new DisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(metrics);
            //int width = metrics.widthPixels;
            //int height = metrics.heightPixels;

            FragmentManager fragmentManager = getSupportFragmentManager();

            if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
               (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //if ((width > height && width > 1023) || (width < height && height > 1023)){
                //code for big screen (like tablet)

                //setContentView(R.layout.activity_main_large);
                //FragmentManager fragmentManager = getSupportFragmentManager();

                // Add the fragment to the 'fragment_container' FrameLayout
                fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
                fragmentManager.beginTransaction().add(R.id.detail_fragment_container, detailViewFragment).commit();

            }else{
                //code for small screen (like smartphone)




                // Add the fragment to the 'fragment_container' FrameLayout
                fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
            }
        }
        */
    }


    public void onMovieItemSelected(View view) {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //code for big screen (like tablet)

        }else{
            //code for small screen (like smartphone)
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, detailViewFragment).commit();
        }
    }


    public void backToList(View view) {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //code for big screen (like tablet)

        }else{
            //code for small screen (like smartphone)
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, listViewFragment).commit();
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
