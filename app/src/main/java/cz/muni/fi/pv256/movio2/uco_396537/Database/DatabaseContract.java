package cz.muni.fi.pv256.movio2.uco_396537.Database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by david on 6.1.17.
 */

public class DatabaseContract
{
    public static final String AUTHORITY = "cz.muni.fi.pv256.movio2.uco_396537";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String MOVIE_PATH = "movie";

    private DatabaseContract() {}

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(MOVIE_PATH).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY + "/" + MOVIE_PATH;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + AUTHORITY + "/" + MOVIE_PATH;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_COVER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
