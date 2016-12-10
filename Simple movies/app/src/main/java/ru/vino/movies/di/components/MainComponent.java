package ru.vino.movies.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.vino.movies.di.modules.ApiModule;
import ru.vino.movies.di.modules.MoviesAppModule;

@Singleton
@Component(modules = {
        ApiModule.class,
        MoviesAppModule.class
})
public interface MainComponent {

    MoviesActivityComponent createMoviesActivityComponent();

    MovieInfoActivityComponent createMovieInfoActivityComponent();

}
