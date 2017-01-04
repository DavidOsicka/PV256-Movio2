package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cz.muni.fi.pv256.movio2.uco_396537.MainActivity;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 23.11.16.
 */

public class DownloadIntentService extends IntentService {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "32bde8b225e94c4cbc6cacbe47c99cf1";
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";
    private static final String POPULAR_MOVIES_URL = "sort_by=popularity.desc";
    private static final String NEW_MOVIES_URL = "primary_release_date.gte=" +
            DateTime.now().minusDays(7).toString("YYYY-MM-dd") + "&primary_release_date.lte=" + DateTime.now().toString("YYYY-MM-dd");

    private Retrofit mRetrofit = null;


    public DownloadIntentService() {
        super(DownloadIntentService.class.getName());
    }

    @Override
    public void onCreate(){
        super.onCreate();

        JsonDeserializer<Long> deserializer = new JsonDeserializer<Long>() {
            @Override
            public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
                if(json==null){
                    return new Long(0);
                }
                else{
                    String dateString = json.getAsString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    long dateLong = 0;
                    try {
                        Date releaseDate = simpleDateFormat.parse(dateString);
                        dateLong = releaseDate.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return new Long(dateLong);
                }
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Long.class, deserializer)
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Override
    protected void onHandleIntent (Intent intent) {

        ArrayList<Movie> movies = new ArrayList<>();
        HashMap<String, Bitmap> pictures = new HashMap<>();
        int movieType = intent.getIntExtra(Model.MOVIE_TYPE, 0);
        DownloadInterface downloadService = mRetrofit.create(DownloadInterface.class);
        Call<MovieListJson> moviesCall = null;

        if(movieType == Model.NEW_MOVIE_TYPE) {
//            moviesCall = downloadService.downloadNewMovies(API_KEY, NEW_MOVIES_URL);
            moviesCall = downloadService.downloadNewMovies();
        } else if(movieType == Model.POPULAR_MOVIE_TYPE) {
            moviesCall = downloadService.downloadPopularMovies(API_KEY, POPULAR_MOVIES_URL);
        }

        try {
            Response<MovieListJson> moviesResponse = moviesCall.execute();
            movies = moviesResponse.body().results;

            if(movies != null) {
                if (!movies.isEmpty()) {
                    for (Movie movie : movies) {
                        if(movie.getBackdrop() != null) {
                            pictures.put(movie.getBackdrop(), Glide.with(this).load(IMAGE_URL + movie.getBackdrop()).asBitmap().into(200, 200).get());
                        }
                        if(movie.getCoverPath() != null) {
                            pictures.put(movie.getCoverPath(), Glide.with(this).load(IMAGE_URL + movie.getCoverPath()).asBitmap().into(200, 200).get());
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

        //Broadcast
        Intent brocastIntent = new Intent();
        brocastIntent.setAction(MainActivity.DataReceiver.LOCAL_DOWNLOAD);
        brocastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        brocastIntent.putExtra(Model.MOVIE_TYPE, movieType);
        brocastIntent.putExtra(MainActivity.DataReceiver.MOVIES, movies);
        brocastIntent.putExtra(MainActivity.DataReceiver.PICTURES, pictures);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(brocastIntent);
    }

    public class MovieListJson {
        public ArrayList<Movie> results;
    }
}
