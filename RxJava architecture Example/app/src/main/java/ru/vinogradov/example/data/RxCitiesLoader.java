package ru.vinogradov.example.data;


import android.content.Context;
import android.support.v4.content.Loader;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxCitiesLoader extends Loader {

    private Subscription subscription;
    private Observable<List<City>> observable;

    public RxCitiesLoader(Context context) {
        super(context);

        observable = ApiFactory.getCitiesService().getCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .cache();

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        subscription = observable.subscribe(this::deliverResult);

    }

    @Override
    protected void onStopLoading() {
        subscription.unsubscribe();
        super.onStopLoading();
    }
}
