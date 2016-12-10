package ru.vino.movies.di.components;

import dagger.Subcomponent;
import ru.vino.movies.di.ActivityScope;
import ru.vino.movies.di.modules.MoviesActivityModule;
import ru.vino.movies.movies.MoviesActivity;

@ActivityScope
@Subcomponent(modules = {MoviesActivityModule.class})
public interface MoviesActivityComponent {

    void inject(MoviesActivity moviesActivity);

}
