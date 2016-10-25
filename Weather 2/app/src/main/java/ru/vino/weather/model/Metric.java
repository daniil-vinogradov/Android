package ru.vino.weather.model;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Metric {

    @SerializedName("Value")
    Double value;
    @SerializedName("Unit")
    String unit;
    @SerializedName("UnitType")
    Integer unitType;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }


    public void setUnit(String unit) {
        this.unit = unit;
    }


    public Integer getUnitType() {
        return unitType;
    }


    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

}
