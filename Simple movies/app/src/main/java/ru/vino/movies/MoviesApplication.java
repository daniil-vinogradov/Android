package ru.vino.movies;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;

import ru.vino.movies.di.components.DaggerMainComponent;
import ru.vino.movies.di.components.MainComponent;
import ru.vino.movies.di.modules.MoviesAppModule;


public class MoviesApplication extends Application {

    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        mainComponent = DaggerMainComponent.builder()
                .moviesAppModule(new MoviesAppModule(this))
                .build();

    }

    public MainComponent getComponent() {
        return mainComponent;
    }

    public static MoviesApplication from(@NonNull Context context) {
        return (MoviesApplication) context.getApplicationContext();
    }

}
