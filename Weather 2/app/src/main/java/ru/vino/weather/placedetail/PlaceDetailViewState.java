package ru.vino.weather.placedetail;


import java.util.List;

import ru.vino.weather.model.DailyForecastModel;

public class PlaceDetailViewState {

    public static final int STATE_SHOW_LOADING = 1;
    public static final int STATE_SHOW_CONTENT = 2;
    public static final int STATE_ERROR = 3;

    private int currentViewState;
    private List<DailyForecastModel> loadedData;

    public void setStateShowContent(List<DailyForecastModel> loadedData) {
        currentViewState = STATE_SHOW_CONTENT;
        this.loadedData = loadedData;
    }

    public void setStateShowLoading() {
        currentViewState = STATE_SHOW_LOADING;
    }

    public void setStateError() {
        currentViewState = STATE_ERROR;
    }

    public int getCurrentViewState() {
        return currentViewState;
    }

    public List<DailyForecastModel> getLoadedData() {
        return loadedData;
    }
}
