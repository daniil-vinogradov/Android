package com.vinogradov.myweather.database;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @author d.vinogradov
 */
public class WeatherForDay extends RealmObject {
    @SerializedName("date")
    private String date;
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("weatherCode")
    private String weatherCode;
    @SerializedName("maxtempC")
    private String maxTempC;
    @SerializedName("mintempC")
    private String minTempC;
    @SerializedName("value")
    private String value;

    public WeatherForDay() {
    }

    public WeatherForDay(WeatherForDay weatherForDay) {
        this.date = weatherForDay.date;
        this.sunrise = weatherForDay.sunrise;
        this.sunset = weatherForDay.sunset;
        this.weatherCode = weatherForDay.weatherCode;
        this.maxTempC = weatherForDay.maxTempC;
        this.minTempC = weatherForDay.minTempC;
        this.value = weatherForDay.value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(String maxTempC) {
        this.maxTempC = maxTempC;
    }

    public String getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(String minTempC) {
        this.minTempC = minTempC;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
