package ru.vino.movies.di.modules;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import ru.vino.movies.di.ActivityScope;
import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.movies.MoviesActivity;
import ru.vino.movies.movies.MoviesContract;
import ru.vino.movies.movies.presenters.NowPlayingBasePresenter;
import ru.vino.movies.movies.presenters.PopularMoviesBasePresenter;
import ru.vino.movies.movies.presenters.UpcomingMoviesBasePresenter;

@Module
public class MoviesActivityModule {

    @ActivityScope
    @Provides
    @IntoMap
    @StringKey(MoviesActivity.POPULAR)
    MoviesContract.AbstractMoviesPresenter providePopularMoviesPresenter(
            IMoviesInteractor interactor) {
        return new PopularMoviesBasePresenter(interactor);
    }

    @ActivityScope
    @Provides
    @IntoMap
    @StringKey(MoviesActivity.NOW_PLAYING)
    MoviesContract.AbstractMoviesPresenter provideNowPlayingMoviesPresenter(
            IMoviesInteractor interactor) {
        return new NowPlayingBasePresenter(interactor);
    }

    @ActivityScope
    @Provides
    @IntoMap
    @StringKey(MoviesActivity.UPCOMING)
    MoviesContract.AbstractMoviesPresenter provideUpcomingMoviesPresenter(
            IMoviesInteractor interactor) {
        return new UpcomingMoviesBasePresenter(interactor);
    }

}
