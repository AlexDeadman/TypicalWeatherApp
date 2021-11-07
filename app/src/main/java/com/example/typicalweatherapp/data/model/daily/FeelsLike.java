package com.example.typicalweatherapp.data.model.daily;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeelsLike {

    @SerializedName("day")
    @Expose
    private Double day;

    @SerializedName("night")
    @Expose
    private Double night;

    @SerializedName("eve")
    @Expose
    private Double eve;

    @SerializedName("morn")
    @Expose
    private Double morn;

    public FeelsLike(
            Double day,
            Double night,
            Double eve,
            Double morn
    ) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public FeelsLike() {
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

}
