package com.vinogradov.myweather.database;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author d.vinogradov
 */
public class City extends RealmObject {

    private String name;

    @PrimaryKey
    private String coord;

    @SerializedName("FeelsLikeC")
    private String feelsLikeC;

    @SerializedName("temp_C")
    private String temp_C;

    @SerializedName("weatherCode")
    private String weatherCode;

    @SerializedName("weather")
    private RealmList<WeatherForDay> weatherForDays;

    private String image;


    public City() {
    }


    public City(City newCity) {
        this.name = newCity.getName();
        this.coord = newCity.getCoord();
        this.feelsLikeC = newCity.getFeelsLikeC();
        this.temp_C = newCity.getTemp_C();
        this.weatherCode = newCity.getWeatherCode();
        this.weatherForDays = new RealmList<WeatherForDay>();
        this.weatherForDays.addAll(newCity.getWeatherForDays());
        this.image = newCity.getImage();
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public void setTemp_C(String temp_C) {
        this.temp_C = temp_C;
    }

    public RealmList<WeatherForDay> getWeatherForDays() {
        return weatherForDays;
    }

    public void setWeatherForDays(RealmList<WeatherForDay> weatherForDays) {
        this.weatherForDays = weatherForDays;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

}
