package ru.vino.movies.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.vino.movies.di.ActivityScope;
import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.movieinfo.MovieInfoContract;
import ru.vino.movies.movieinfo.MovieInfoPresenterImpl;

@Module
public class MovieInfoActivityModule {

    @ActivityScope
    @Provides
    MovieInfoContract.AbstractMovieInfoPresenter provideMovieInfoPresenter(
            IMoviesInteractor interactor) {
        return new MovieInfoPresenterImpl(interactor);
    }

}
