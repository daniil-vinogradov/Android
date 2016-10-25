package ru.vino.weather.databases.tables;


import android.support.annotation.NonNull;

public class CurrentConditionTable {

    @NonNull
    public static final String TABLE_CURRENT_CONDITION = "current_condition";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_NAME = "name";

    @NonNull
    public static final String COLUMN_AREA = "area";

    @NonNull
    public static final String COLUMN_EPOCH_TIME = "epoch_time";

    @NonNull
    public static final String COLUMN_WEATHER_TEXT = "weather_text";

    @NonNull
    public static final String COLUMN_WEATHER_ICON = "weather_icon";

    @NonNull
    public static final String COLUMN_DAY = "day";

    @NonNull
    public static final String COLUMN_TEMPERATURE = "temperature";

    @NonNull
    public static final String COLUMN_ADD_TIME = "add_time";

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_CURRENT_CONDITION + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_AREA + " TEXT NOT NULL, "
                + COLUMN_EPOCH_TIME + " INTEGER NOT NULL, "
                + COLUMN_WEATHER_TEXT + " TEXT NOT NULL, "
                + COLUMN_WEATHER_ICON + " INTEGER NOT NULL, "
                + COLUMN_DAY + " INTEGER NOT NULL, "
                + COLUMN_TEMPERATURE + " TEXT NOT NULL, "
                + COLUMN_ADD_TIME + " INTEGER NOT NULL "
                + ");";
    }


}
