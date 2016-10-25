package ru.vino.weather.di.modules;


import android.content.Context;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.vino.weather.WeatherApplication;
import ru.vino.weather.placedetail.PlaceDetailContract;
import ru.vino.weather.placedetail.PlaceDetailPresenterImpl;
import ru.vino.weather.places.PlacesContract;
import ru.vino.weather.places.PlacesPresenterImpl;
import ru.vino.weather.interactors.IWeatherInteractor;
import ru.vino.weather.interactors.WeatherInteractorImpl;
import ru.vino.weather.search.SearchContract;
import ru.vino.weather.search.SearchPresenterImpl;

@Module
public class WeatherAppModule {

    WeatherApplication application;

    public WeatherAppModule(WeatherApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    IWeatherInteractor provideInteractor(@Named("weather") Retrofit retrofitWeather,
                                         @Named("photos") Retrofit retrofitPhotos,
                                         StorIOSQLite storIOSQLite) {
        return new WeatherInteractorImpl(retrofitWeather, retrofitPhotos, storIOSQLite);
    }

    @Provides
    SearchContract.AbstractSearchPresenter provideSearchPresenter(IWeatherInteractor interactor) {
        return new SearchPresenterImpl(interactor);
    }

    @Provides
    PlacesContract.AbstractPlacesPresenter providePlacesPresenter(IWeatherInteractor interactor) {
        return new PlacesPresenterImpl(interactor);
    }

    @Provides
    PlaceDetailContract.AbstractPlaceDetailPresenter providePlaceDetailPresenter(IWeatherInteractor interactor) {
        return new PlaceDetailPresenterImpl(interactor);
    }

}
