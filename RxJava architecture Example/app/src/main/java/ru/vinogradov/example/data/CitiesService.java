package ru.vinogradov.example.data;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface CitiesService {

    @GET("data/2.5/weather")
    Observable<List<City>> getCities();

}