package ru.vino.weather.model;


import com.google.gson.annotations.SerializedName;

public class TemperatureForecast {

    @SerializedName("Minimum")
    private Minimum minimum;
    @SerializedName("Maximum")
    private Maximum maximum;

    /**
     * @return The minimum
     */
    public Minimum getMinimum() {
        return minimum;
    }

    /**
     * @param minimum The Minimum
     */
    public void setMinimum(Minimum minimum) {
        this.minimum = minimum;
    }

    /**
     * @return The maximum
     */
    public Maximum getMaximum() {
        return maximum;
    }

    /**
     * @param maximum The Maximum
     */
    public void setMaximum(Maximum maximum) {
        this.maximum = maximum;
    }

}
