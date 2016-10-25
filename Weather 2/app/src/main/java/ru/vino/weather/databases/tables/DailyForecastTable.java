package ru.vino.weather.databases.tables;

import android.support.annotation.NonNull;


public class DailyForecastTable {

    @NonNull
    public static final String TABLE_DAILY_FORECAST = "daily_forecast";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_PLACE_ID = "place_id";

    @NonNull
    public static final String COLUMN_EPOCH_TIME = "epoch_time";

    @NonNull
    public static final String COLUMN_DAY = "day";

    @NonNull
    public static final String COLUMN_TEMPERATURE = "temperature";

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_DAILY_FORECAST + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_PLACE_ID + " TEXT NOT NULL, "
                + COLUMN_EPOCH_TIME + " INTEGER NOT NULL, "
                + COLUMN_DAY + " TEXT NOT NULL, "
                + COLUMN_TEMPERATURE + " INTEGER NOT NULL "
                + ");";
    }

}
