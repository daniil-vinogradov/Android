package ru.vino.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import ru.vino.weather.databases.tables.CurrentConditionTable;
import ru.vino.weather.databases.tables.PhotoTable;

@StorIOSQLiteType(table = PhotoTable.TABLE_PHOTO)
public class PhotoModel {

    @StorIOSQLiteColumn(name = PhotoTable.COLUMN_ID, key = true)
    Long id;

    @StorIOSQLiteColumn(name = PhotoTable.COLUMN_URL)
    @SerializedName("url_l")
    String urlL;

    @SerializedName("views")
    Integer views;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlL() {
        return urlL;
    }

    public void setUrlL(String urlL) {
        this.urlL = urlL;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
