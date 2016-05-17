package ru.vinogradov.example.cities;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import ru.vinogradov.example.data.City;
import ru.vinogradov.example.data.RxCitiesLoader;

public class CitiesPresenter implements CitiesContract.Presenter, LoaderManager.LoaderCallbacks<List<City>> {

    private final static int CITIES_LOADER = 1;

    private CitiesContract.View citiesView;
    private RxCitiesLoader loader;
    private LoaderManager loaderManager;

    public CitiesPresenter(CitiesContract.View citiesView, RxCitiesLoader loader, LoaderManager loaderManager) {
        this.citiesView = citiesView;
        this.loader = loader;
        this.loaderManager = loaderManager;
    }

    @Override
    public void loadCities() {
        loaderManager.initLoader(CITIES_LOADER, Bundle.EMPTY, this);
    }

    @Override
    public Loader<List<City>> onCreateLoader(int id, Bundle args) {
        citiesView.showProgressBar();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<City>> loader, List<City> data) {
        if (data == null)
            citiesView.setErrorMessage();
        else citiesView.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<List<City>> loader) {

    }
}
