package ru.vino.weather.databases.resolvers;


import android.database.Cursor;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.databases.tables.DailyForecastTable;
import ru.vino.weather.model.DailyForecastModel;
import ru.vino.weather.model.Day;
import ru.vino.weather.model.Temperature;
import ru.vino.weather.model.TemperatureForecast;

public class DailyForecastGetResolver extends DefaultGetResolver<DailyForecastModel> {

    @NonNull
    @Override
    public DailyForecastModel mapFromCursor(@NonNull Cursor cursor) {
        Gson gson = new Gson();

        DailyForecastModel object = new DailyForecastModel(
                cursor.getLong(cursor.getColumnIndexOrThrow(DailyForecastTable.COLUMN_ID)),
                cursor.getLong(cursor.getColumnIndexOrThrow(DailyForecastTable.COLUMN_EPOCH_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DailyForecastTable.COLUMN_DAY)),
                cursor.getString(cursor.getColumnIndexOrThrow(DailyForecastTable.COLUMN_TEMPERATURE)));

        Day day = gson.fromJson(object.getDaySerialized(), Day.class);
        object.setDay(day);
        TemperatureForecast temperature = gson.fromJson(object.getTemperatureSerialized(), TemperatureForecast.class);
        object.setTemperature(temperature);


        return object;
    }

}
