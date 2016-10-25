package ru.vino.weather.interactors;


import android.os.Build;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import ru.vino.weather.BuildConfig;
import ru.vino.weather.api.WeatherApi;
import ru.vino.weather.databases.resolvers.CurrentConditionGetResolver;
import ru.vino.weather.databases.resolvers.CurrentConditionPutResolver;
import ru.vino.weather.databases.resolvers.DailyForecastGetResolver;
import ru.vino.weather.databases.resolvers.DailyForecastPutResolver;
import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.databases.tables.DailyForecastTable;
import ru.vino.weather.databases.tables.PhotoTable;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.DailyForecastModel;
import ru.vino.weather.model.PhotoModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import ru.vino.weather.model.responsemodels.DailyForecastResponseModel;
import ru.vino.weather.model.responsemodels.PhotosResponseModel;
import rx.Observable;

public class WeatherInteractorImpl implements IWeatherInteractor {

    private static final String WEATHER_KEY = BuildConfig.API_KEY_WEATHER;
    private static final String PHOTOS_KEY = BuildConfig.API_KEY_PHOTOS;
    private static final String PHOTOS_GROUP = "1463451@N25";
    private static final String PHOTOS_METHOD = "flickr.groups.pools.getPhotos";
    private static final String PHOTOS_EXTRAS = "url_l,views";

    Retrofit retrofitWeather;
    Retrofit retrofitPhotos;
    StorIOSQLite storIOSQLite;

    @Inject
    public WeatherInteractorImpl(Retrofit retrofitWeather,
                                 Retrofit retrofitPhotos,
                                 StorIOSQLite storIOSQLite) {
        this.retrofitWeather = retrofitWeather;
        this.retrofitPhotos = retrofitPhotos;
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Observable<List<AutocompleteResponseModel>> searchPlace(String q) {
        return retrofitWeather
                .create(WeatherApi.class)
                .searchPlace(WEATHER_KEY, q);
    }

    @Override
    public Observable<CurrentConditionModel> getCurrentCondition(long id) {
        return retrofitWeather
                .create(WeatherApi.class)
                .getCurrentCondition(id, WEATHER_KEY)
                .map(currentConditionModels -> currentConditionModels.get(0));
    }

    @Override
    public Observable<List<CurrentConditionModel>> getCachedCurrentCondition() {
        return storIOSQLite
                .get()
                .listOfObjects(CurrentConditionModel.class)
                .withQuery(Query.builder()
                        .table(CurrentConditionTable.TABLE_CURRENT_CONDITION)
                        .orderBy(CurrentConditionTable.COLUMN_ADD_TIME)
                        .build())
                .withGetResolver(new CurrentConditionGetResolver())
                .prepare()
                .asRxObservable();
    }

    @Override
    public PutResult cacheCurrentCondition(CurrentConditionModel currentCondition) {
        return storIOSQLite
                .put()
                .object(currentCondition)
                .withPutResolver(new CurrentConditionPutResolver())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public PutResults<CurrentConditionModel> cacheCurrentConditions(List<CurrentConditionModel> currentConditions) {
        return storIOSQLite
                .put()
                .objects(currentConditions)
                .withPutResolver(new CurrentConditionPutResolver())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public DeleteResult deleteCurrentCondition(CurrentConditionModel currentCondition) {
        return storIOSQLite
                .delete()
                .object(currentCondition)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Observable<PhotosResponseModel> getPhoto(String q) {
        return retrofitPhotos
                .create(WeatherApi.class)
                .getPhoto(PHOTOS_METHOD, PHOTOS_KEY, PHOTOS_GROUP, q, PHOTOS_EXTRAS, "json", 1);
    }

    @Override
    public Observable<PhotoModel> getCachedPhoto(Long id) {
        return storIOSQLite
                .get()
                .object(PhotoModel.class)
                .withQuery(Query.builder()
                        .table(PhotoTable.TABLE_PHOTO)
                        .where(PhotoTable.COLUMN_ID + " = ?")
                        .whereArgs(id)
                        .build())
                .prepare()
                .asRxObservable();
    }

    @Override
    public PutResult cachePhoto(PhotoModel photo) {
        return storIOSQLite
                .put()
                .object(photo)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Observable<DailyForecastResponseModel> getForecast(long id) {
        return retrofitWeather
                .create(WeatherApi.class)
                .getForecast(id, WEATHER_KEY, true);
    }

    @Override
    public PutResults<DailyForecastModel> cacheForecast(List<DailyForecastModel> dailyForecasts) {
        return storIOSQLite
                .put()
                .objects(dailyForecasts)
                .withPutResolver(new DailyForecastPutResolver())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public DeleteResult deleteForecast(long id) {
        return storIOSQLite
                .delete()
                .byQuery(DeleteQuery.builder()
                        .table(DailyForecastTable.TABLE_DAILY_FORECAST)
                        .where(DailyForecastTable.COLUMN_PLACE_ID + " = ?")
                        .whereArgs(id)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Observable<List<DailyForecastModel>> getCachedForecast(long id) {
        return storIOSQLite
                .get()
                .listOfObjects(DailyForecastModel.class)
                .withQuery(Query.builder()
                        .table(DailyForecastTable.TABLE_DAILY_FORECAST)
                        .where(DailyForecastTable.COLUMN_PLACE_ID + " = ?")
                        .whereArgs(id)
                        .build())
                .withGetResolver(new DailyForecastGetResolver())
                .prepare()
                .asRxObservable();
    }

}
