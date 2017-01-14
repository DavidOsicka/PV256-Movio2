package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import cz.muni.fi.pv256.movio2.uco_396537.ListViewFragment;
import cz.muni.fi.pv256.movio2.uco_396537.R;


/**
 * Created by david on 29.10.16.
 */

public class Model {

    public static final String MOVIE_TYPE = "Movie type";
    public static final int NEW_MOVIE_TYPE = 1, POPULAR_MOVIE_TYPE = 2;

    private ArrayList<Object> mNewMovies = new ArrayList<Object>();
    private ArrayList<Object> mPopularMovies = new ArrayList<Object>();
    private HashMap<String, Bitmap> mPictures = new HashMap<>();
    private static Model sInstance = null;
    private WeakReference<ListViewFragment> mContext = null;


    private Model() { }

    public static Model getInstance() {
        if(sInstance == null) {
            sInstance = new Model();
        }
        return sInstance;
    }

    public void setContext(ListViewFragment listViewFragment) {
        mContext = new WeakReference<>(listViewFragment);
    }

    public void setNewMovies(ArrayList<Movie> movies) {
        mNewMovies.clear();
        if(mContext != null) {
            mNewMovies.add(mContext.get().getResources().getString(R.string.category_new_movies));
        } else {
            mNewMovies.add(new String("NEW MOVIES"));
        }
        mNewMovies.addAll(movies);
    }

    public void setPopularMovies(ArrayList<Movie> movies) {
        mPopularMovies.clear();
        if(mContext != null) {
            mNewMovies.add(mContext.get().getResources().getString(R.string.category_popular_movies));
        } else {
            mPopularMovies.add(new String("POPULAR MOVIES"));
        }
        mPopularMovies.addAll(movies);
    }

    public ArrayList<Object> getMovies() {
        ArrayList<Object> items = new ArrayList<Object>();
        items.addAll(mNewMovies);
        items.addAll(mPopularMovies);
        return items;
    }

    public void setPicture(HashMap<String, Bitmap> pictures) {
        mPictures.putAll(pictures);
    }

    public Bitmap getPicture(String key) {
        if(mPictures.containsKey(key)) {
            return mPictures.get(key);
        }
        return Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565);
    }

    public void reloadData() {
        if(mContext != null) {
            if(mContext.get() != null) {
                mContext.get().reloadData();
            }
        }
    }
}
