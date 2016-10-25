package ru.vino.weather.databases;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.databases.tables.DailyForecastTable;
import ru.vino.weather.databases.tables.PhotoTable;

public class DbOpenHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "weather_db";
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CurrentConditionTable.getCreateTableQuery());
        db.execSQL(PhotoTable.getCreateTableQuery());
        db.execSQL(DailyForecastTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
