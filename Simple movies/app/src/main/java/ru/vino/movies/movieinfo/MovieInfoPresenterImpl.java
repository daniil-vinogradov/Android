package ru.vino.movies.movieinfo;


import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.viewstate.BaseViewState;
import ru.vino.movies.viewstate.MovieInfoViewState;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieInfoPresenterImpl extends MovieInfoContract.AbstractMovieInfoPresenter {

    private IMoviesInteractor interactor;
    private MovieInfoViewState viewState;

    public MovieInfoPresenterImpl(IMoviesInteractor interactor) {
        this.interactor = interactor;
        viewState = new MovieInfoViewState();
        setViewState(viewState);
    }

    @Override
    public void getMovieInfo(long id) {
        if (viewState.getCurrentViewState() == BaseViewState.STATE_SHOW_CONTENT)
            return;
        viewState.showProgress();

        interactor
                .getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fullMovieModel -> {
                    viewState.presentMovieInfo(fullMovieModel);
                }, throwable -> throwable.printStackTrace());
    }
}
