package ru.vino.weather.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.databases.tables.DailyForecastTable;

@StorIOSQLiteType(table = DailyForecastTable.TABLE_DAILY_FORECAST)
public class DailyForecastModel {

    @StorIOSQLiteColumn(name = DailyForecastTable.COLUMN_ID, key = true)
    Long id;
    @StorIOSQLiteColumn(name = DailyForecastTable.COLUMN_PLACE_ID)
    long placeId;
    @StorIOSQLiteColumn(name = DailyForecastTable.COLUMN_EPOCH_TIME)
    @SerializedName("EpochDate")
    long epochDate;
    @SerializedName("Day")
    Day day;
    @StorIOSQLiteColumn(name = DailyForecastTable.COLUMN_DAY)
    String daySerialized;
    @SerializedName("Temperature")
    TemperatureForecast temperature;
    @StorIOSQLiteColumn(name = DailyForecastTable.COLUMN_TEMPERATURE)
    String temperatureSerialized;

    public DailyForecastModel() {
    }

    public DailyForecastModel(long id, long epochDate, String daySerialized, String temperatureSerialized) {
        this.id = id;
        this.epochDate = epochDate;
        this.daySerialized = daySerialized;
        this.temperatureSerialized = temperatureSerialized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getDaySerialized() {
        return daySerialized;
    }

    public void setDaySerialized(String daySerialized) {
        this.daySerialized = daySerialized;
    }

    public TemperatureForecast getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureForecast temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureSerialized() {
        return temperatureSerialized;
    }

    public void setTemperatureSerialized(String temperatureSerialized) {
        this.temperatureSerialized = temperatureSerialized;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }
}
