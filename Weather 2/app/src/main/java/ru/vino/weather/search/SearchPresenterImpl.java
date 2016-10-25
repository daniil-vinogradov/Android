package ru.vino.weather.search;


import javax.inject.Inject;

import ru.vino.weather.interactors.IWeatherInteractor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenterImpl extends SearchContract.AbstractSearchPresenter {

    IWeatherInteractor interactor;

    @Inject
    public SearchPresenterImpl(IWeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void search(String q) {
        interactor
                .searchPlace(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    if (isViewAttached())
                        getView().presentResults(results);
                }, throwable -> {
                    throwable.printStackTrace();
                    if (isViewAttached())
                        getView().showError();
                });
    }

}
