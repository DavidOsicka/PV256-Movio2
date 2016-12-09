package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.graphics.Bitmap;
import android.util.ArrayMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.ListViewFragment;


/**
 * Created by david on 29.10.16.
 */

public class Model {

    private ArrayList<Object> mItems = new ArrayList<Object>();
    private ArrayMap<String, Bitmap> mPictures = new ArrayMap<>();

    private static Model sInstance = null;
    private boolean mDownloaded = false;
    private WeakReference<ListViewFragment> mContext = null;


    private Model() {}

    public static Model getInstance() {
        if(sInstance == null) {
            sInstance = new Model();
        }
        return sInstance;
    }

    public void setContext(ListViewFragment listViewFragment) {
        mContext = new WeakReference<>(listViewFragment);
        //DownloadClient.getsInstance().setContext(listViewFragment);
        if(!mDownloaded) {
            //DownloadClient.getsInstance().execute();
            mDownloaded = true;
        }
    }

    public  void setMovies(ArrayList<Object> movies) {
        mItems = movies;
        if(mContext != null) {
            mContext.get().reloadData();
        }
    }

    public ArrayList<Object> getMovies() {
        return mItems;
    }

    public void setPicture(String key, Bitmap picture) {
        mPictures.put(key, picture);
        if(mContext != null) {
            //mContext.get().reloadData();
        }
    }

    public Bitmap getPicture(String key) {
        if(mPictures.containsKey(key)) {
            return mPictures.get(key);
        }
        return Bitmap.createBitmap(0,0, Bitmap.Config.RGB_565);
    }
}
