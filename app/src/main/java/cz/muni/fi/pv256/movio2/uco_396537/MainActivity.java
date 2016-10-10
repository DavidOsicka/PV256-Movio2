package cz.muni.fi.pv256.movio2.uco_396537;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        App app = new App();
        app.onCreate();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.list_fragment_container) != null && findViewById(R.id.detail_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
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



}
