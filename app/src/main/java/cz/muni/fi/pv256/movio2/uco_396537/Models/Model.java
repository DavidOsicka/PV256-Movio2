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

    private ArrayList<Object> mMovies = new ArrayList<>();
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

    public void setMovies(ArrayList<Object> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
    }

    public ArrayList<Object> getMovies() {
        return mMovies;
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
