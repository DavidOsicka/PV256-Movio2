package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.muni.fi.pv256.movio2.uco_396537.ListViewFragment;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


/**
 * Created by david on 7.11.16.
 */

public class DownloadClient extends AsyncTask<Void, Void, ArrayList<Object>> {

    private static DownloadClient sInstance = null;

    private static final String NEW_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?api_key=32bde8b225e94c4cbc6cacbe47c99cf1&primary_release_date.gte=" +
            DateTime.now().minusDays(7).toString("YYYY-MM-dd") + "&primary_release_date.lte=" + DateTime.now().toString("YYYY-MM-dd");

    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?api_key=32bde8b225e94c4cbc6cacbe47c99cf1&sort_by=popularity.desc";

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private ArrayList<Movie> mNewMovies = new ArrayList<Movie>();
    private ArrayList<Movie> mPopularMovies = new ArrayList<Movie>();
    private ArrayList<Object> mItems = new ArrayList<Object>();

    private WeakReference<ListViewFragment> mContext = null;


    private DownloadClient() {}

    public static DownloadClient getsInstance() {
        if(sInstance == null) {
            sInstance = new DownloadClient();
        }
        return sInstance;
    }

    public void setContext(ListViewFragment listViewFragment) {
        mContext = new WeakReference<>(listViewFragment);
    }

    @Override
    protected ArrayList<Object> doInBackground(Void... voids) {

        downloadData(new Request.Builder().url(NEW_MOVIES_URL).build(), mNewMovies);
        downloadData(new Request.Builder().url(POPULAR_MOVIES_URL).build(), mPopularMovies);

        mItems.add(new String("NEW MOVIES"));
        mItems.addAll(mNewMovies);
        mItems.add(new String("POPULAR MOVIES"));
        mItems.addAll(mPopularMovies);

        return mItems;
    }

    @Override
    protected void onPostExecute(ArrayList<Object> result) {
        Model.getInstance().setMovies(result);
    }


    private void downloadData(Request request, ArrayList<Movie> movieList) {

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isCancelled() && response == null) {
            return;
        }

        Gson parser = new Gson();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            JSONObject data = new JSONObject(response.body().string());
            JSONArray moviesArray_json = data.getJSONArray("results");

            for(int i=0; i<moviesArray_json.length(); i++) {
                JSONObject movie_json = moviesArray_json.getJSONObject(i);
                Movie movie = parser.fromJson(movie_json.toString(), Movie.class);

                Date releaseDate = dateFormat.parse(movie_json.getString("release_date"));
                movie.setReleaseDate(releaseDate.getTime());

                if(mContext != null) {
                    Model.getInstance().setPicture(movie.getBackdrop(), Glide.with(mContext.get().getContext()).load(IMAGE_URL + movie.getBackdrop()).asBitmap().into(200, 200).get());
                    Model.getInstance().setPicture(movie.getCoverPath(), Glide.with(mContext.get().getContext()).load(IMAGE_URL + movie.getCoverPath()).asBitmap().into(200, 200).get());
                }

                movieList.add(movie);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } catch(ExecutionException e) {
            e.printStackTrace();
        }
    }
}
