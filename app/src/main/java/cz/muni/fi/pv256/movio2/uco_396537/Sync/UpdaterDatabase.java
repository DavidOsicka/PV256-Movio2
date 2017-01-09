package cz.muni.fi.pv256.movio2.uco_396537.Sync;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396537.Database.DatabaseManager;
import cz.muni.fi.pv256.movio2.uco_396537.Download.DownloadClient;
import cz.muni.fi.pv256.movio2.uco_396537.Download.DownloadInterface;
import cz.muni.fi.pv256.movio2.uco_396537.MainActivity;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 8.1.17.
 */

public class UpdaterDatabase {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "32bde8b225e94c4cbc6cacbe47c99cf1";

    private Context context;
    private WeakReference<MainActivity> mMainActivity = null;
    private static UpdaterDatabase sInstance = null;

    private UpdaterDatabase() { }

    public static UpdaterDatabase getInstance( ) {
        if (sInstance == null) {
            sInstance = new UpdaterDatabase();
        }
        return sInstance;
    }

    public void init(Context context){
        this.context = context.getApplicationContext();
    }

    public void setMainActivityContext(MainActivity mainActivity) {
        mMainActivity = new WeakReference<MainActivity>(mainActivity);
    }

    public void updateMovieInDatabase() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                DatabaseManager databaseManager = new DatabaseManager(context);
                DownloadInterface downloadService = retrofit.create(DownloadInterface.class);
                ArrayList<Movie> moviesDatabase = databaseManager.getAllMovies();
                boolean updated = false;

                for (Movie oldMovie : moviesDatabase) {
                    final Call<Movie> call = downloadService.downloadMovie(oldMovie.getId(), API_KEY);

                    try {
                        Response<Movie> newPostResponse = call.execute();
                        int statusCode = newPostResponse.code();

                        if (statusCode == 200) {
                            Movie newMovie = newPostResponse.body();

                            if(!compareStrNull(oldMovie.getTitle(), newMovie.getTitle()) || !compareStrNull(oldMovie.getReleaseDate(), newMovie.getReleaseDate()) ||
                                    !compareStrNull(oldMovie.getCover(), newMovie.getCover()) || !compareStrNull(oldMovie.getBackdrop(), newMovie.getBackdrop()) ||
                                    oldMovie.getPopularity() != newMovie.getPopularity() || !compareStrNull(oldMovie.getOverview(), newMovie.getOverview()))
                            {
                                databaseManager.updateMovie(newMovie);
                                updated = true;
                            }
                        } else {
                            Log.i("Error", "Can not update movies");
                        }
                    } catch (IOException e) {
                        Log.i("Error", e.toString());
                    }
                }
                if(!updated) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(
                            new Runnable() {
                                @Override
                                public void run() { Toast.makeText(context, "Movies are actual", Toast.LENGTH_SHORT).show();}
                            }
                    );
                } else {
                    if(mMainActivity != null) {
                        mMainActivity.get().updatingFinished();
                    }
                }
            }
        };

        thread.start();
    }

    public static boolean compareStrNull(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}
