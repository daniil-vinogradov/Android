package ru.vino.weather.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.vino.weather.placedetail.PlaceDetailFragment;
import ru.vino.weather.places.PlacesFragment;
import ru.vino.weather.di.modules.ApiModule;
import ru.vino.weather.di.modules.DbModule;
import ru.vino.weather.di.modules.WeatherAppModule;
import ru.vino.weather.search.SearchFragment;

@Singleton
@Component(modules = {
        WeatherAppModule.class,
        ApiModule.class,
        DbModule.class
})
public interface WeatherAppComponent {
    void inject(SearchFragment searchFragment);

    void inject(PlacesFragment placesFragment);

    void inject(PlaceDetailFragment placeDetailFragment);
}
