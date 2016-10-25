package ru.vino.weather.model;


import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("Icon")
    private Integer icon;
    @SerializedName("IconPhrase")
    private String iconPhrase;

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

}
