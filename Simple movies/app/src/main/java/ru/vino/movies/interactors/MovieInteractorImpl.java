package ru.vino.movies.interactors;


import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import retrofit2.Retrofit;
import ru.vino.movies.api.MoviesApi;
import ru.vino.movies.model.FullMovieModel;
import ru.vino.movies.model.responsemodels.MoviesResponseModel;
import rx.Observable;

public class MovieInteractorImpl implements IMoviesInteractor {

    private static String API_KEY = "507a0d40a66ade4064ca20389e6e93e5";

    Retrofit retrofit;
    StorIOSQLite storIOSQLite;

    public MovieInteractorImpl(Retrofit retrofit) {
        this.retrofit = retrofit;
        //this.storIOSQLite = storIOSQLite;
    }


    @Override
    public Observable<MoviesResponseModel> getPopularMovies(int page) {
        return retrofit
                .create(MoviesApi.class)
                .getPopularMovies(API_KEY, "en-US", page);
    }

    @Override
    public Observable<MoviesResponseModel> getNowPlayingMovies(int page) {
        return retrofit
                .create(MoviesApi.class)
                .getNowPlayingMovies(API_KEY, "en-US", page);
    }

    @Override
    public Observable<MoviesResponseModel> getUpcomingMovies(int page) {
        return retrofit
                .create(MoviesApi.class)
                .getUpcomingMovies(API_KEY, "en-US", page);
    }

    @Override
    public Observable<FullMovieModel> getMovieDetail(long movieId) {
        return retrofit
                .create(MoviesApi.class)
                .getMovieDetail(movieId, API_KEY, "en-US", "credits");
    }
}
