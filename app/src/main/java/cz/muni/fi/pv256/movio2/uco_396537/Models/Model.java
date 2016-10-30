package cz.muni.fi.pv256.movio2.uco_396537.Models;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396537.R;

/**
 * Created by david on 29.10.16.
 */

public class Model {

    public static final ArrayList<Object> ITEMS = new ArrayList<Object>();

    static {

        ITEMS.add(new String("HOROR"));
        ITEMS.add(new Movie("Film 1", 1991, String.valueOf(R.drawable.walking_dead_cover), String.valueOf(R.drawable.walking_dead_backdrop), 0.0f));
        ITEMS.add(new String("COMEDY"));
        ITEMS.add(new Movie("Film 2", 1992, "", "", 1.0f));
        ITEMS.add(new String("HISTORICAL"));
        ITEMS.add(new Movie("Film 3", 1993, "", "", 2.0f));
        ITEMS.add(new String("FANTASY & SCI-FI"));
    }
}
