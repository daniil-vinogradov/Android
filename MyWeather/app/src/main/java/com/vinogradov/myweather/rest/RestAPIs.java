package com.vinogradov.myweather.rest;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author d.vinogradov
 */
public class RestAPIs {

    public interface GeocodeAPI {
        @GET("/maps/api/geocode/json?key=AIzaSyD21bWqQxhuu3hUPmLX8Nk6SoxQ2nYGfbA&region=ru")
        Call<ResponseBody> getCityLatLng(@Query("address") String city);
    }

    public interface WeatherAPI {
        @GET("/free/v2/weather.ashx?format=json&num_of_days=10&tp=24&key=cedac72c514df734e671e48b8c611")
        Call<ResponseBody> getWeather(@Query("q") String city);
    }

    public interface SearchAPI {
        @GET("/customsearch/v1?cx=018241668336631813339:bvamitayz94&key=AIzaSyBOTGjWi-cTLd8N9O7QTSr-D4PjbdCCkBU&searchType=image&imgSize=large")
        Call<ResponseBody> getImage(@Query("q") String city);
    }


}
