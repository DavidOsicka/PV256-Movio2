package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "pref";
    private static final String THEME_NAME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        App app = new App();
        app.onCreate();

        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            setTheme(R.style.AppTheme1);
        } else {
            setTheme(R.style.AppTheme2);
        }
        setContentView(R.layout.activity_main);


        // Check that the activity is using the layout version with the fragment_container FrameLayout
        if (findViewById(R.id.list_fragment_container) != null && findViewById(R.id.detail_fragment_container) != null) {

            // However, if we're being restored from a previous state, then we don't need to do anything and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ListViewFragment listViewFragment = new ListViewFragment();
            //listViewFragment.setAppContext(this);
            DetailViewFragment detailViewFragment = new DetailViewFragment();

            // Check the display size
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            if (width > 1023 || height > 1023){
                //code for big screen (like tablet)

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction().add(R.id.list_fragment_container, listViewFragment).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.detail_fragment_container, detailViewFragment).commit();

            }else{
                //code for small screen (like smartphone)

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction().add(R.id.list_fragment_container, listViewFragment).commit();
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
