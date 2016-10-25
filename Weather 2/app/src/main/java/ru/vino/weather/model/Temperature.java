package ru.vino.weather.model;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Temperature {

    @SerializedName("Metric")
    Metric metric;
    @SerializedName("Imperial")
    Imperial imperial;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Imperial getImperial() {
        return imperial;
    }

    public void setImperial(Imperial imperial) {
        this.imperial = imperial;
    }

}
