package cz.muni.fi.pv256.movio2.uco_396537;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import cz.muni.fi.pv256.movio2.uco_396537.Models.DownloadClient;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String ARG_SHOW_DETAIL = "show_detail";
    private static final String PREFERENCES_NAME = "pref";
    private static final String THEME_NAME = "theme";

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private DataReceiver mReceiver = null;
    private NotificationManager mNotificationManager = null;
    private ListViewFragment mListViewFragment = null;
    private DetailViewFragment mDetailViewFragment = null;
    private boolean isTablet = false;
//    public boolean downloading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
        Log.d(TAG, " onCreate method");

        // This starts a class with strict mode
        App app = new App();
        app.onCreate();

        // This is used to reload theme from preferences when swithing themes
        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            setTheme(R.style.AppTheme1);
        } else {
            setTheme(R.style.AppTheme2);
        }

        isTablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;


        if(isTablet) {
            //code for big screen (like tablet)
            setContentView(R.layout.activity_main_large);
        }else{
            //code for small screen (like smartphone)
            setContentView(R.layout.activity_main);
        }

        mListViewFragment = new ListViewFragment();
        mDetailViewFragment = new DetailViewFragment();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        downloadingNotification();

        Intent newMovieDownloadIntent = new Intent(this, DownloadClient.class);
        newMovieDownloadIntent.putExtra(Model.MOVIE_TYPE, Model.NEW_MOVIE_TYPE);
        startService(newMovieDownloadIntent);

        Intent popularMovieDownloadIntent = new Intent(this, DownloadClient.class);
        popularMovieDownloadIntent.putExtra(Model.MOVIE_TYPE, Model.POPULAR_MOVIE_TYPE);
        startService(popularMovieDownloadIntent);

        // we're being restored from a previous state
        if (savedInstanceState != null) {
            return;
        }

        if(findViewById(R.id.main_fragment_container) != null) {
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, mListViewFragment).commit();
        }
        if(findViewById(R.id.detail_fragment_container) != null) {
            fragmentManager.beginTransaction().add(R.id.detail_fragment_container, mDetailViewFragment).commit();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Log.d(TAG, " onStart method");

        mReceiver = new DataReceiver();
        mReceiver.setContext(this);
        IntentFilter intentFilter = new IntentFilter(MainActivity.DataReceiver.LOCAL_DOWNLOAD);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        Log.d(TAG, " onResume method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        Log.d(TAG, " onPause method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        Log.d(TAG, " onStop method");

        if(mReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        Log.d(TAG, " onDestroy method");
    }


    public void onMovieClick(int item) {
        if(item < 0 || item >= Model.getInstance().getMovies().size()) {
            return;
        }
        Intent intent = new Intent(this, DetailViewFragment.class);
        Bundle bundle = new Bundle();
        if(Model.getInstance().getMovies().get(item) instanceof Movie) {
            intent.putExtra("Movie", (Movie) Model.getInstance().getMovies().get(item));
            bundle = intent.getExtras();
        }
        bundle.putBoolean(ARG_SHOW_DETAIL, true);
        //bundle.putInt(ARG_MOVIE_ID, item);

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

    public void onMovieLongClick(int item) {
        if(item < 0 || item >= Model.getInstance().getMovies().size()) {
            return;
        }
        if(Model.getInstance().getMovies().get(item) instanceof Movie) {
            Toast.makeText(this, ((Movie) Model.getInstance().getMovies().get(item)).getTitle(), Toast.LENGTH_SHORT).show();
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

    public void downloadingNotification() {
        if(mNotificationManager == null) {
            return;
        }
        NotificationCompat.Builder downloadingNotification = new NotificationCompat.Builder(this)
                .setContentTitle("Downloading")
                .setContentText("Movio2 downloading data")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(0, 0, true)
                .setAutoCancel(true);

        mNotificationManager.notify(0, downloadingNotification.build());
        if(mListViewFragment != null) {
            mListViewFragment.setDownloading(true);
        }
//        downloading = true;
    }

    public void finishedNotification() {
        if(mNotificationManager == null) {
            return;
        }
        NotificationCompat.Builder downloadingNotification = new NotificationCompat.Builder(this)
                .setContentTitle("Downloading finished")
                .setContentText("Movio2 finished downloading of data")
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setAutoCancel(true);

        mNotificationManager.notify(0, downloadingNotification.build());
        if(mListViewFragment != null) {
            mListViewFragment.setDownloading(false);
        }
//        downloading = false;
    }

    public void errorNotification() {
        if(mNotificationManager == null) {
            return;
        }
        NotificationCompat.Builder downloadingNotification = new NotificationCompat.Builder(this)
                .setContentTitle("ERROR")
                .setContentText("Movio2 can not download data")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setAutoCancel(true);

        mNotificationManager.notify(0, downloadingNotification.build());
    }

    public class DataReceiver extends BroadcastReceiver {

        public static final String LOCAL_DOWNLOAD = "cz.muni.fi.pv256.movio2.uco_396537.Model.intent.action.LOCAL_DOWNLOAD";
        public static final String MOVIES = "movies";
        public static final String PICTURES = "pictures";
        public static final String ERROR = "error";

        private WeakReference<MainActivity> mContext = null;

        public void setContext(MainActivity mainActivity) {
            mContext = new WeakReference<>(mainActivity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getBooleanExtra(ERROR, false)) {
                if(mContext != null) {
                    mContext.get().errorNotification();
                }

            } else {
                int movieType = intent.getIntExtra(Model.MOVIE_TYPE, 0);
                ArrayList<Movie> movies = intent.getParcelableArrayListExtra(MOVIES);
                HashMap<String, Bitmap> pictures = (HashMap<String, Bitmap>)intent.getSerializableExtra(PICTURES);
                ArrayList<Object> items = new ArrayList<Object>();

                if(movieType == Model.NEW_MOVIE_TYPE) {
                    Model.getInstance().setNewMovies(movies);
                } else if(movieType == Model.POPULAR_MOVIE_TYPE) {
                    Model.getInstance().setPopularMovies(movies);
                    if(mContext != null) {
                        mContext.get().finishedNotification();
                    }
                }
                Model.getInstance().setPicture(pictures);
                Model.getInstance().reloadData();
            }
        }
    }


}
