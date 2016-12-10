package ru.vino.movies.movies.presenters;


import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.model.responsemodels.MoviesResponseModel;
import ru.vino.movies.movies.MoviesContract;
import ru.vino.movies.viewstate.BaseViewState;
import ru.vino.movies.viewstate.MoviesViewState;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class MoviesBasePresenter extends MoviesContract.AbstractMoviesPresenter {

    private IMoviesInteractor interactor;
    private MoviesViewState viewState;

    private int page = 1;
    private int totalPages;

    public MoviesBasePresenter(IMoviesInteractor interactor) {
        this.interactor = interactor;
        viewState = new MoviesViewState();
        setViewState(viewState);
    }

    @Override
    public void getMovies(boolean pagination) {

        if (viewState.getCurrentViewState() != BaseViewState.NO_STATE && !pagination)
            return;

        if (viewState.getCurrentViewState() == BaseViewState.STATE_SHOW_PAGINATION_LOADING && pagination)
            return;

        if (page == totalPages)
            return;

        if (pagination)
            viewState.showPaginationProgress();
        else viewState.showProgress();

        getObservable(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(moviesResponseModel -> totalPages = moviesResponseModel.getTotalPages())
                .subscribe(moviesResponseModel -> {
                    page++;
                    viewState.presentMovies(moviesResponseModel.getMovies());
                }, throwable -> {
                    throwable.printStackTrace();
                    viewState.showError();
                });
    }

    @Override
    public void saveState(Bundle bundle) {
        viewState.saveState(bundle);
        bundle.putInt("page", page);
    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {
        viewState.restoreState(bundle);
        if (bundle != null)
            if (page == 1)
                page = bundle.getInt("page", 1);
    }

    public IMoviesInteractor getInteractor() {
        return interactor;
    }

    protected abstract Observable<MoviesResponseModel> getObservable(int page);

}
