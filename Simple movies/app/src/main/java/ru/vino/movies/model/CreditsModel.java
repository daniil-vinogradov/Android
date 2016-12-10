package ru.vino.movies.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsModel {

    @SerializedName("cast")
    @Expose
    List<CastModel> cast = null;
    @SerializedName("crew")
    @Expose
    List<CrewModel> crew = null;

    public List<CastModel> getCast() {
        return cast;
    }

    public void setCast(List<CastModel> cast) {
        this.cast = cast;
    }

    public List<CrewModel> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewModel> crew) {
        this.crew = crew;
    }
}
