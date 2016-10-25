package ru.vino.weather.databases.resolvers;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import ru.vino.weather.model.CurrentConditionModel;

public class CurrentConditionPutResolver extends PutResolver<CurrentConditionModel> {

    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull CurrentConditionModel object) {

        Gson gson = new Gson();
        String temperatureSerialized = gson.toJson(object.getTemperature());
        object.setTemperatureSerialized(temperatureSerialized);

        return storIOSQLite
                .put()
                .object(object)
                .prepare()
                .executeAsBlocking();
    }

}
