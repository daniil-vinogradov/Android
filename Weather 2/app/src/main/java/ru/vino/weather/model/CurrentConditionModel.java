package ru.vino.weather.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import org.parceler.Parcel;

import ru.vino.weather.databases.tables.CurrentConditionTable;

@Parcel
@StorIOSQLiteType(table = CurrentConditionTable.TABLE_CURRENT_CONDITION)
public class CurrentConditionModel {

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_ID, key = true)
    long id;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_NAME)
    String name;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_AREA)
    String area;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_EPOCH_TIME)
    @SerializedName("EpochTime")
    long epochTime;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_WEATHER_TEXT)
    @SerializedName("WeatherText")
    String weatherText;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_WEATHER_ICON)
    @SerializedName("WeatherIcon")
    int weatherIcon;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_DAY)
    @SerializedName("IsDayTime")
    Short isDayTime;

    @SerializedName("Temperature")
    Temperature temperature;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_TEMPERATURE)
    String temperatureSerialized;

    @StorIOSQLiteColumn(name = CurrentConditionTable.COLUMN_ADD_TIME)
    long addTime;

    public CurrentConditionModel() {
    }

    public CurrentConditionModel(long id, String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public CurrentConditionModel(long id, String name, String area, long epochTime, String weatherText, int weatherIcon, Short isDayTime, String temperatureSerialized, long addTime) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.epochTime = epochTime;
        this.weatherText = weatherText;
        this.weatherIcon = weatherIcon;
        this.isDayTime = isDayTime;
        this.temperatureSerialized = temperatureSerialized;
        this.addTime = addTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureSerialized() {
        return temperatureSerialized;
    }

    public void setTemperatureSerialized(String temperatureSerialized) {
        this.temperatureSerialized = temperatureSerialized;
    }

    public void setIsDayTime(Short isDayTime) {
        this.isDayTime = isDayTime;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentConditionModel that = (CurrentConditionModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
