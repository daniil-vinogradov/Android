package ru.vino.movies.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreModel {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("name")
    @Expose
    String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
