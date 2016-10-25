package ru.vino.weather.databases.resolvers;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import ru.vino.weather.model.DailyForecastModel;

public class DailyForecastPutResolver extends PutResolver<DailyForecastModel> {

    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull DailyForecastModel object) {
        Gson gson = new Gson();

        String temperatureSerialized = gson.toJson(object.getTemperature());
        String daySerialized = gson.toJson(object.getDay());
        object.setTemperatureSerialized(temperatureSerialized);
        object.setDaySerialized(daySerialized);

        return storIOSQLite
                .put()
                .object(object)
                .prepare()
                .executeAsBlocking();
    }

}
