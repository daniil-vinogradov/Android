package ru.vino.weather;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;

import ru.vino.weather.di.components.DaggerWeatherAppComponent;
import ru.vino.weather.di.components.WeatherAppComponent;
import ru.vino.weather.di.modules.ApiModule;
import ru.vino.weather.di.modules.DbModule;
import ru.vino.weather.di.modules.WeatherAppModule;

public class WeatherApplication extends Application {

    private WeatherAppComponent weatherAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);

        weatherAppComponent = DaggerWeatherAppComponent.builder()
                .weatherAppModule(new WeatherAppModule(this))
                .dbModule(new DbModule())
                .apiModule(new ApiModule())
                .build();

    }

    public WeatherAppComponent getComponent() {
        return weatherAppComponent;
    }

    public static WeatherApplication from(@NonNull Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

}
