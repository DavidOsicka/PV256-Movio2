package cz.muni.fi.pv256.movio2.uco_396537.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by david on 2.12.16.
 */

public interface DownloadInterface {
    @GET("3/discover/movie")
    Call<DownloadIntentService.MovieListJson> downloadPopularMovies(@Query("api_key") String apiKey, @Query("&") String MovieSection);

//    @GET("3/discover/movie")
//    Call<DownloadIntentService.MovieListJson> downloadNewMovies(@Query("api_key") String apiKey, @Query("&") String MovieSection);

    @GET("3/discover/movie?api_key=32bde8b225e94c4cbc6cacbe47c99cf1&primary_release_date.gte=2016-12-27&primary_release_date.lte=2017-01-03")
    Call<DownloadIntentService.MovieListJson> downloadNewMovies();
}
