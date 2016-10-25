package ru.vino.weather.api;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import ru.vino.weather.model.responsemodels.DailyForecastResponseModel;
import ru.vino.weather.model.responsemodels.PhotosResponseModel;
import rx.Observable;

public interface WeatherApi {

    String WEATHER_ENDPOINT_URL = "http://dataservice.accuweather.com/";
    String PHOTOS_ENDPOINT_URL = "https://api.flickr.com/";

    @GET("/locations/v1/cities/autocomplete")
    Observable<List<AutocompleteResponseModel>> searchPlace(@Query("apikey") String key,
                                                            @Query("q") String q);

    @GET("/currentconditions/v1/{id}")
    Observable<List<CurrentConditionModel>> getCurrentCondition(@Path("id") long id,
                                                                @Query("apikey") String key);

    @GET("/services/rest/")
    Observable<PhotosResponseModel> getPhoto(@Query("method") String method,
                                             @Query("api_key") String key,
                                             @Query("group_id") String groupId,
                                             @Query("tags") String place,
                                             @Query("extras") String extras,
                                             @Query("format") String format,
                                             @Query("nojsoncallback") int nojsoncallback);

    @GET("/forecasts/v1/daily/5day/{id}")
    Observable<DailyForecastResponseModel> getForecast(@Path("id") long id,
                                                       @Query("apikey") String key,
                                                       @Query("metric") boolean metric);

}
