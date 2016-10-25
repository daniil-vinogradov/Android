package ru.vino.weather.interactors;


import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.DailyForecastModel;
import ru.vino.weather.model.PhotoModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import ru.vino.weather.model.responsemodels.DailyForecastResponseModel;
import ru.vino.weather.model.responsemodels.PhotosResponseModel;
import rx.Observable;

public interface IWeatherInteractor {

    Observable<List<AutocompleteResponseModel>> searchPlace(String q);

    Observable<CurrentConditionModel> getCurrentCondition(long id);

    Observable<List<CurrentConditionModel>> getCachedCurrentCondition();

    PutResult cacheCurrentCondition(CurrentConditionModel currentCondition);

    PutResults<CurrentConditionModel> cacheCurrentConditions(List<CurrentConditionModel> currentConditions);

    DeleteResult deleteCurrentCondition(CurrentConditionModel currentCondition);

    Observable<PhotosResponseModel> getPhoto(String q);

    Observable<PhotoModel> getCachedPhoto(Long id);

    PutResult cachePhoto(PhotoModel photo);

    Observable<DailyForecastResponseModel> getForecast(long id);

    PutResults<DailyForecastModel> cacheForecast(List<DailyForecastModel> dailyForecasts);

    DeleteResult deleteForecast(long id);

    Observable<List<DailyForecastModel>> getCachedForecast(long id);

}
