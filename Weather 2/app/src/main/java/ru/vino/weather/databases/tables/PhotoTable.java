package ru.vino.weather.databases.tables;

import android.support.annotation.NonNull;


public class PhotoTable {

    @NonNull
    public static final String TABLE_PHOTO = "photo";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_URL = "url";

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_PHOTO + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_URL + " TEXT NOT NULL "
                + ");";
    }

}
