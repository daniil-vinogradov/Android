package ru.vino.movies.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.vino.movies.MoviesApplication;
import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.interactors.MovieInteractorImpl;

@Module
public class MoviesAppModule {

    MoviesApplication application;

    public MoviesAppModule(MoviesApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    IMoviesInteractor provideInteractor(Retrofit retrofit) {
        return new MovieInteractorImpl(retrofit);
    }

}
