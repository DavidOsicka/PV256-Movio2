package cz.muni.fi.pv256.movio2.uco_396537;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

import static org.junit.Assert.assertEquals;

/**
 * Created by david on 12.1.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieUnitTest {

    Movie movie;

    @Before
    public void setUp() {
        movie = new Movie(2, "title", "releaseDate", "cover", "backdrop", 1.0f, "overview");
    }

    @Test
    public void testID() throws Exception {
        int id = 1;
        movie.setId(id);
        assertEquals(id, movie.getId());
    }

    @Test
    public void testTitle() throws Exception {
        String name = "movie name";
        movie.setTitle(name);
        assertEquals(name, movie.getTitle());
    }

    @Test
    public void testReleaseDate() throws Exception {
        String releaseDate = "12-2-2010";
        movie.setReleaseDate(releaseDate);
        assertEquals(releaseDate, movie.getReleaseDate());
    }

    @Test
    public void testCover() throws Exception {
        String coverPath = "some/path/to/cover";
        movie.setCover(coverPath);
        assertEquals(coverPath, movie.getCover());
    }

    @Test
    public void testBackdrop() throws Exception {
        String backdropPath = "some/path/to/backdrop";
        movie.setBackdrop(backdropPath);
        assertEquals(backdropPath, movie.getBackdrop());
    }

    @Test
    public void testPopularity() throws Exception {
        float popularity = 1.5f;
        movie.setPopularity(popularity);
        assertEquals(popularity, movie.getPopularity(), 0.01f);
    }

    @Test
    public void testOverview() throws Exception {
        String overview = "some description of movie";
        movie.setOverview(overview);
        assertEquals(overview, movie.getOverview());
    }
}
