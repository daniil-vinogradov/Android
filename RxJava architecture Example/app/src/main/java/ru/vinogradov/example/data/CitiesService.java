package ru.vinogradov.example.data;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface CitiesService {

    @GET("/daniil-vinogradov/JavaAndroid/master/RxJava%20architecture%20Example/cities.json")
    Observable<List<City>> getCities();

}