package ru.vino.weather.databases.resolvers;


import android.database.Cursor;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.Temperature;

public class CurrentConditionGetResolver extends DefaultGetResolver<CurrentConditionModel> {
    @NonNull
    @Override
    public CurrentConditionModel mapFromCursor(@NonNull Cursor cursor) {
        Gson gson = new Gson();

        CurrentConditionModel object = new CurrentConditionModel(
                cursor.getLong(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_AREA)),
                cursor.getLong(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_EPOCH_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_WEATHER_TEXT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_WEATHER_ICON)),
                cursor.getShort(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_DAY)),
                cursor.getString(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_TEMPERATURE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(CurrentConditionTable.COLUMN_ADD_TIME)));
        Temperature temperature = gson.fromJson(object.getTemperatureSerialized(), Temperature.class);
        object.setTemperature(temperature);

        return object;
    }
}
