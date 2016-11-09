package cz.muni.fi.pv256.movio2.uco_396537.Models;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.R;

/**
 * Created by david on 29.10.16.
 */

public class Model {

//    public static final ArrayList<Object> ITEMS = new ArrayList<Object>();
    private ArrayList<Object> mItems = new ArrayList<Object>();
    private static Model sInstance = null;


    private Model() {}

    public static Model getInstance() {
        if(sInstance == null) {
            sInstance = new Model();
        }
        return sInstance;
    }





//    static {
//
//        ITEMS.add(new String("HOROR"));
//        ITEMS.add(new Movie("Walking dead", 2010, String.valueOf(R.drawable.walking_dead_cover), String.valueOf(R.drawable.walking_dead_backdrop), 0.0f));
//        ITEMS.add(new String("ROMANTIC"));
//        ITEMS.add(new Movie("The Vampire Diaries", 2009, String.valueOf(R.drawable.vampire_diaries_cover), String.valueOf(R.drawable.vampire_diaries_backdrop), 1.0f));
//        ITEMS.add(new String("ACTION"));
//        ITEMS.add(new Movie("Inferno", 2016, String.valueOf(R.drawable.inferno_cover), String.valueOf(R.drawable.inferno_backdrop), 2.0f));
//        ITEMS.add(new String("FANTASY & SCI-FI"));
//        ITEMS.add(new Movie("No name movie with no data", 1993, "", "", 2.0f));
//    }
}
