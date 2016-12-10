package ru.vino.movies.movies.presenters;

import ru.vino.movies.interactors.IMoviesInteractor;
import ru.vino.movies.model.responsemodels.MoviesResponseModel;
import rx.Observable;


public class UpcomingMoviesBasePresenter extends MoviesBasePresenter {

    public UpcomingMoviesBasePresenter(IMoviesInteractor interactor) {
        super(interactor);
    }

    @Override
    protected Observable<MoviesResponseModel> getObservable(int page) {
        return getInteractor().getUpcomingMovies(page);
    }

}
