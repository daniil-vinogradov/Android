package ru.vino.movies.api;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.vino.movies.model.FullMovieModel;
import ru.vino.movies.model.responsemodels.MoviesResponseModel;
import rx.Observable;

public interface MoviesApi {

    String ENDPOINT_URL = "https://api.themoviedb.org/";

    @GET("/3/movie/popular/")
    Observable<MoviesResponseModel> getPopularMovies(@Query("api_key") String key,
                                                     @Query("language") String language,
                                                     @Query("page") int page);

    @GET("/3/movie/now_playing/")
    Observable<MoviesResponseModel> getNowPlayingMovies(@Query("api_key") String key,
                                                        @Query("language") String language,
                                                        @Query("page") int page);

    @GET("/3/movie/upcoming/")
    Observable<MoviesResponseModel> getUpcomingMovies(@Query("api_key") String key,
                                                      @Query("language") String language,
                                                      @Query("page") int page);

    @GET("/3/movie/{id}")
    Observable<FullMovieModel> getMovieDetail(@Path("id") long movieId,
                                              @Query("api_key") String key,
                                              @Query("language") String language,
                                              @Query("append_to_response") String credits);

}
