package com.vinogradov.myweather.rest;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Retrofit;

/**
 * @author d.vinogradov
 */
public class ApiFactory {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
    }


    @NonNull
    public static RestAPIs.GeocodeAPI getGeocodeAPI(String url) {
        return getRetrofit(url).create(RestAPIs.GeocodeAPI.class);
    }

    @NonNull
    public static RestAPIs.WeatherAPI getWeatherAPI(String url) {
        return getRetrofit(url).create(RestAPIs.WeatherAPI.class);
    }

    @NonNull
    public static RestAPIs.SearchAPI getSearchAPI(String url) {
        return getRetrofit(url).create(RestAPIs.SearchAPI.class);
    }

    @NonNull
    private static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(CLIENT)
                .build();
    }
}
