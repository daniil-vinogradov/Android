package ru.vino.weather.model.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.vino.weather.model.DailyForecastModel;


public class DailyForecastResponseModel {

    @SerializedName("DailyForecasts")
    @Expose
    private List<DailyForecastModel> dailyForecastModels = new ArrayList<DailyForecastModel>();

    public List<DailyForecastModel> getDailyForecastModels() {
        return dailyForecastModels;
    }

    public void setDailyForecastModels(List<DailyForecastModel> dailyForecastModels) {
        this.dailyForecastModels = dailyForecastModels;
    }
}
