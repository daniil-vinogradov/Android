package ru.vino.movies.viewstate;


import ru.vino.movies.model.FullMovieModel;
import ru.vino.movies.movieinfo.MovieInfoContract;

public class MovieInfoViewState extends
        BaseViewState<MovieInfoContract.IMovieInfoView, FullMovieModel> implements MovieInfoContract.IMovieInfoView {

    @Override
    public void attachView(MovieInfoContract.IMovieInfoView view) {
        super.attachView(view);
        if (getCurrentViewState() == STATE_SHOW_CONTENT)
            if (isViewAttached())
                getView().presentMovieInfo(getData());
    }

    @Override
    public void presentMovieInfo(FullMovieModel movie) {
        setData(movie);
        setCurrentViewState(BaseViewState.STATE_SHOW_CONTENT);
        if (isViewAttached())
            getView().presentMovieInfo(getData());
    }

    @Override
    public void showProgress() {
        setCurrentViewState(BaseViewState.STATE_SHOW_LOADING);
        if (isViewAttached())
            getView().showProgress();
    }

    @Override
    public void showError() {

    }

}
