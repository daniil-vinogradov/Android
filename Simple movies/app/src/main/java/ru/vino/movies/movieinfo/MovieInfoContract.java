package ru.vino.movies.movieinfo;


import ru.vino.movies.base.BasePresenter;
import ru.vino.movies.model.FullMovieModel;

public interface MovieInfoContract {

    interface IMovieInfoView {
        void presentMovieInfo(FullMovieModel movie);

        void showProgress();

        void showError();
    }

    abstract class AbstractMovieInfoPresenter extends BasePresenter<IMovieInfoView, FullMovieModel> {

        public abstract void getMovieInfo(long id);

    }
}
