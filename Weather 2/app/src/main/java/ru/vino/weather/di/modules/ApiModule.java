package ru.vino.weather.di.modules;

import android.util.Log;

import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vino.weather.api.WeatherApi;
import ru.vino.weather.utils.BooleanTypeAdapter;

@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message ->
                Log.d("OK HTTP", message));

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Named("weather")
    @Provides
    @Singleton
    Retrofit providesWeatherRetrofit(OkHttpClient okHttpClient) {
        GsonBuilder builder = new GsonBuilder();
        BooleanTypeAdapter booleanTypeAdapter = new BooleanTypeAdapter();
        builder.registerTypeAdapter(Short.class, booleanTypeAdapter);
        builder.registerTypeAdapter(short.class, booleanTypeAdapter);
        return new Retrofit.Builder()
                .baseUrl(WeatherApi.WEATHER_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Named("photos")
    @Provides
    @Singleton
    Retrofit providesPhotosRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(WeatherApi.PHOTOS_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
