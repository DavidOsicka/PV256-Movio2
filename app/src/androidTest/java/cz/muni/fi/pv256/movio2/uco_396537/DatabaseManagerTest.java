package cz.muni.fi.pv256.movio2.uco_396537;

import android.test.AndroidTestCase;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396537.Database.DatabaseContract;
import cz.muni.fi.pv256.movio2.uco_396537.Database.DatabaseManager;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 8.1.17.
 */

public class DatabaseManagerTest extends AndroidTestCase {

    private DatabaseManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new DatabaseManager(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                DatabaseContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }


    public void testCreateMovie() throws Exception {
        Movie movie = new Movie(1, "my Movie", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie);

        List<Movie> result = mManager.getAllMovies();

        assertTrue(result.size() == 1);
    }

    public void testGetAllMovies() throws Exception {
        Movie movie1 = new Movie(1, "my Movie", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie1);
        Movie movie2 = new Movie(2, "my Movie number 2", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie2);

        List<Movie> result = mManager.getAllMovies();

        assertTrue(result.size() == 2);
        assertEquals(movie1.getTitle(), result.get(0).getTitle());
        assertEquals(movie2.getTitle(), result.get(1).getTitle());
    }

    public void testGetMovieById() throws Exception {
        Movie movie = new Movie(1, "my Movie", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());

        assertTrue(result.size() == 1);
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }

    public void testUpdateMovie() throws Exception  {
        Movie movie = new Movie(1, "my Movie", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie);

        movie.setTitle("my movie number 2");
        mManager.updateMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());

        assertTrue(result.size() == 1);
        assertEquals(movie.getTitle(), result.get(0).getTitle());
    }

    public void testDeleteMovie() throws Exception {
        Movie movie = new Movie(1, "my Movie", "14-1-1991", "cover", "backdrop", 1.5f, "overview");
        mManager.createMovie(movie);

        List<Movie> result = mManager.getMovieById(movie.getId());
        assertTrue(result.size() == 1);

        mManager.deleteMovie(movie);

        List<Movie> result2 = mManager.getMovieById(movie.getId());
        assertTrue(result2.size() == 0);
    }
}
