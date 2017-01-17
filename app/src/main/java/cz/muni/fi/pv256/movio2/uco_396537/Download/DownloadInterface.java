package cz.muni.fi.pv256.movio2.uco_396537.Download;

import cz.muni.fi.pv256.movio2.uco_396537.Download.DownloadClient;
import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by david on 2.12.16.
 */

public interface DownloadInterface {
    @GET("3/discover/movie")
    Call<DownloadClient.MovieListJson> downloadPopularMovies(@Query("api_key") String apiKey, @Query("sort_by") String MovieSection);
//    "https://api.themoviedb.org/3/discover/movie?api_key=32bde8b225e94c4cbc6cacbe47c99cf1&sort_by=popularity.desc"

    @GET("3/discover/movie")
    Call<DownloadClient.MovieListJson> downloadNewMovies(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String MovieSection);
//    "https://api.themoviedb.org/3/discover/movie?api_key=32bde8b225e94c4cbc6cacbe47c99cf1&primary_release_date.gte=2016-12-27&primary_release_date.lte=2017-01-03"

    @GET("3/movie/{id}")
    Call<Movie> downloadMovie(@Path("id") long id, @Query("api_key") String apiKey);
}
