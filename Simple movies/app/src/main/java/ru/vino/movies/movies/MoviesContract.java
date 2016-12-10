package ru.vino.movies.movies;


import java.util.ArrayList;
import java.util.List;

import ru.vino.movies.base.BasePresenter;
import ru.vino.movies.base.Restorable;
import ru.vino.movies.model.ShortMovieModel;

public interface MoviesContract {

    interface IMoviesView {
        void presentMovies(List<ShortMovieModel> movies);

        void showProgress();

        void showPaginationProgress();

        void showError();
    }

    abstract class AbstractMoviesPresenter extends BasePresenter<IMoviesView, ArrayList<ShortMovieModel>>
            implements Restorable {

        public abstract void getMovies(boolean pagination);

    }


}
