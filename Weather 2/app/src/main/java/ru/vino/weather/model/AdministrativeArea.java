package ru.vino.weather.model;

import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {

    @SerializedName("ID")
    private String id;
    @SerializedName("LocalizedName")
    private String localizedName;

    public String getID() {
        return id;
    }

    public void setID(String iD) {
        this.id = iD;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

}
