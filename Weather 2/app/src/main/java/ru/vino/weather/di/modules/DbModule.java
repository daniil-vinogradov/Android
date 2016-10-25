package ru.vino.weather.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vino.weather.databases.DbOpenHelper;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.CurrentConditionModelStorIOSQLiteDeleteResolver;
import ru.vino.weather.model.CurrentConditionModelStorIOSQLiteGetResolver;
import ru.vino.weather.model.CurrentConditionModelStorIOSQLitePutResolver;
import ru.vino.weather.model.DailyForecastModel;
import ru.vino.weather.model.DailyForecastModelStorIOSQLiteDeleteResolver;
import ru.vino.weather.model.DailyForecastModelStorIOSQLiteGetResolver;
import ru.vino.weather.model.DailyForecastModelStorIOSQLitePutResolver;
import ru.vino.weather.model.PhotoModel;
import ru.vino.weather.model.PhotoModelStorIOSQLiteDeleteResolver;
import ru.vino.weather.model.PhotoModelStorIOSQLiteGetResolver;
import ru.vino.weather.model.PhotoModelStorIOSQLitePutResolver;

@Module
public class DbModule {

    @Provides
    @NonNull
    @Singleton
    public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(CurrentConditionModel.class, SQLiteTypeMapping.<CurrentConditionModel>builder()
                        .putResolver(new CurrentConditionModelStorIOSQLitePutResolver())
                        .getResolver(new CurrentConditionModelStorIOSQLiteGetResolver())
                        .deleteResolver(new CurrentConditionModelStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(PhotoModel.class, SQLiteTypeMapping.<PhotoModel>builder()
                        .putResolver(new PhotoModelStorIOSQLitePutResolver())
                        .getResolver(new PhotoModelStorIOSQLiteGetResolver())
                        .deleteResolver(new PhotoModelStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(DailyForecastModel.class, SQLiteTypeMapping.<DailyForecastModel>builder()
                        .putResolver(new DailyForecastModelStorIOSQLitePutResolver())
                        .getResolver(new DailyForecastModelStorIOSQLiteGetResolver())
                        .deleteResolver(new DailyForecastModelStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull Context context) {
        return new DbOpenHelper(context);
    }

}
