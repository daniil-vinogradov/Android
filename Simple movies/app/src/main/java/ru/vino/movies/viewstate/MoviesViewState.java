package ru.vino.movies.viewstate;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.vino.movies.base.Restorable;
import ru.vino.movies.model.ShortMovieModel;
import ru.vino.movies.movies.MoviesContract;

public class MoviesViewState extends BaseViewState<MoviesContract.IMoviesView, ArrayList<ShortMovieModel>>
        implements MoviesContract.IMoviesView, Restorable {

    @Override
    public void attachView(MoviesContract.IMoviesView view) {
        super.attachView(view);
        switch (getCurrentViewState()) {
            case STATE_SHOW_CONTENT:
                getView().presentMovies(getData());
                break;
            case STATE_SHOW_PAGINATION_LOADING:
                getView().presentMovies(getData());
                break;
            case STATE_SHOW_LOADING:
                getView().showProgress();
                break;
            case STATE_ERROR:
                getView().showError();
                break;
        }
    }

    @Override
    public void presentMovies(List<ShortMovieModel> movies) {
        if (getData() == null)
            setData((ArrayList<ShortMovieModel>) movies);
        else {
            if (getData().get(getData().size() - 1) == null)
                getData().remove(getData().size() - 1);
            getData().addAll(movies);
        }
        setCurrentViewState(BaseViewState.STATE_SHOW_CONTENT);
        if (isViewAttached())
            getView().presentMovies(movies);
    }

    @Override
    public void showProgress() {
        setCurrentViewState(BaseViewState.STATE_SHOW_LOADING);
        if (isViewAttached())
            getView().showProgress();
    }

    @Override
    public void showPaginationProgress() {
        if (getData() != null)
            getData().add(null);
        setCurrentViewState(BaseViewState.STATE_SHOW_PAGINATION_LOADING);
        if (isViewAttached())
            getView().showPaginationProgress();
    }

    @Override
    public void showError() {
        setCurrentViewState(STATE_ERROR);
        if (isViewAttached())
            getView().showError();
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putSerializable("data", getData());
        bundle.putInt("state", getCurrentViewState());
    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {
        if (bundle != null) {
            if (getCurrentViewState() == NO_STATE) {
                setData((ArrayList<ShortMovieModel>) bundle.getSerializable("data"));
                setCurrentViewState(bundle.getInt("state"));
            }
        }
    }
}
