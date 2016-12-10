package ru.vino.movies.interactors;


import ru.vino.movies.model.FullMovieModel;
import ru.vino.movies.model.responsemodels.MoviesResponseModel;
import rx.Observable;

public interface IMoviesInteractor {

    Observable<MoviesResponseModel> getPopularMovies(int page);

    Observable<MoviesResponseModel> getNowPlayingMovies(int page);

    Observable<MoviesResponseModel> getUpcomingMovies(int page);

    Observable<FullMovieModel> getMovieDetail(long movieId);

}
