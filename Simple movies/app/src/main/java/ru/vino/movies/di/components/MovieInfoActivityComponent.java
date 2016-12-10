package ru.vino.movies.di.components;

import dagger.Subcomponent;
import ru.vino.movies.di.ActivityScope;
import ru.vino.movies.di.modules.MovieInfoActivityModule;
import ru.vino.movies.movieinfo.MovieInfoActivity;

@ActivityScope
@Subcomponent(modules = {MovieInfoActivityModule.class})
public interface MovieInfoActivityComponent {

    void inject(MovieInfoActivity movieInfoActivity);

}
