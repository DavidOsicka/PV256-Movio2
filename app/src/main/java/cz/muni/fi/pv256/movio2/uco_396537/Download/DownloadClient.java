package cz.muni.fi.pv256.movio2.uco_396537.Download;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cz.muni.fi.pv256.movio2.uco_396537.MainActivity;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Model;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;
import cz.muni.fi.pv256.movio2.uco_396537.R;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 23.11.16.
 */

public class DownloadClient extends IntentService {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_LOW_RESOLUTION = "w300";
    private static final String IMAGE_HIGH_RESOLUTION = "w500";
    private static final String POPULAR_MOVIES_URL = "popularity.desc";
    private static final String NEW_MOVIES_URL = DateTime.now().minusDays(7).toString("YYYY-MM-dd");

    private Retrofit mRetrofit = null;


    public DownloadClient() {
        super(DownloadClient.class.getName());
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    protected void onHandleIntent (Intent intent) {

        ArrayList<Movie> movies = new ArrayList<>();
        HashMap<String, Bitmap> pictures = new HashMap<>();
        DownloadInterface downloadService = mRetrofit.create(DownloadInterface.class);
        Call<MovieListJson> moviesCall = null;
        moviesCall = downloadService.downloadNewMovies(getResources().getString(R.string.api_key), NEW_MOVIES_URL);


        try {
            Response<MovieListJson> moviesResponse = moviesCall.execute();
            movies = moviesResponse.body().results;

            if(movies != null) {
                if (!movies.isEmpty()) {
                    for (Movie movie : movies) {
                        if(movie.getBackdrop() != null) {
                            pictures.put(movie.getBackdrop(), Glide.with(this).load(IMAGE_URL + IMAGE_HIGH_RESOLUTION    + movie.getBackdrop()).asBitmap().into(200, 200).get());
                        }
                        if(movie.getCover() != null) {
                            pictures.put(movie.getCover(), Glide.with(this).load(IMAGE_URL + IMAGE_LOW_RESOLUTION + movie.getCover()).asBitmap().into(200, 200).get());
                        }
                    }
                }
            }
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }


        ArrayList<Movie> movies1 = new ArrayList<>();
        DownloadInterface downloadService1 = mRetrofit.create(DownloadInterface.class);
        Call<MovieListJson> moviesCall1 = null;
        moviesCall1 = downloadService1.downloadPopularMovies(getResources().getString(R.string.api_key), POPULAR_MOVIES_URL);

        try {
            Response<MovieListJson> moviesResponse1 = moviesCall1.execute();
            movies1 = moviesResponse1.body().results;

            if(movies1 != null) {
                if (!movies1.isEmpty()) {
                    for (Movie movie1 : movies1) {
                        if(movie1.getBackdrop() != null) {
                            pictures.put(movie1.getBackdrop(), Glide.with(this).load(IMAGE_URL + IMAGE_HIGH_RESOLUTION    + movie1.getBackdrop()).asBitmap().into(200, 200).get());
                        }
                        if(movie1.getCover() != null) {
                            pictures.put(movie1.getCover(), Glide.with(this).load(IMAGE_URL + IMAGE_LOW_RESOLUTION + movie1.getCover()).asBitmap().into(200, 200).get());
                        }
                    }
                }
            }
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }


        if(movies.isEmpty() && movies1.isEmpty()) {
            Intent brocastIntent = new Intent();
            brocastIntent.setAction(MainActivity.DataReceiver.LOCAL_DOWNLOAD);
            brocastIntent.addCategory(Intent.CATEGORY_DEFAULT);

            brocastIntent.putExtra(MainActivity.DataReceiver.ERROR, true);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(brocastIntent);
        } else {
            Intent brocastIntent = new Intent();
            brocastIntent.setAction(MainActivity.DataReceiver.LOCAL_DOWNLOAD);
            brocastIntent.addCategory(Intent.CATEGORY_DEFAULT);

            ArrayList<Object> all = new ArrayList<>();
            all.add(new String("NEW MOVIES"));
            all.addAll(movies);
            all.add(new String("POPULAR MOVIES"));
            all.addAll(movies1);

            Bundle args = new Bundle();
            args.putSerializable(MainActivity.DataReceiver.MOVIES, all);
            brocastIntent.putExtra("BUNDLE",args);
            brocastIntent.putExtra(MainActivity.DataReceiver.PICTURES, pictures);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(brocastIntent);
        }
    }

    public class MovieListJson {
        public ArrayList<Movie> results;
    }
}
