package ru.vino.weather.model;

import com.google.gson.annotations.SerializedName;


public class Maximum {

    @SerializedName("Value")
    Double value;
    @SerializedName("Unit")
    String unit;
    @SerializedName("UnitType")
    Integer unitType;

    /**
     * @return The value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value The Value
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit The Unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return The unitType
     */
    public Integer getUnitType() {
        return unitType;
    }

    /**
     * @param unitType The UnitType
     */
    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

}
