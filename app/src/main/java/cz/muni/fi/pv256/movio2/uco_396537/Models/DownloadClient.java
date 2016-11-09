package cz.muni.fi.pv256.movio2.uco_396537.Models;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by david on 7.11.16.
 */

public class DownloadClient extends AsyncTask<Void, Void, ArrayList<Object>> {

    private static DownloadClient sInstance = null;


    private DownloadClient() {}

    public static DownloadClient getsInstance() {
        if(sInstance == null) {
            sInstance = new DownloadClient();
        }
        return sInstance;
    }

    @Override
    protected ArrayList<Object> doInBackground(Void... voids) {

    }
}
