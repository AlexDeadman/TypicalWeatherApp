package com.example.typicalweatherapp.data.model;

import com.example.typicalweatherapp.data.model.alert.Alert;
import com.example.typicalweatherapp.data.model.current.Current;
import com.example.typicalweatherapp.data.model.daily.Daily;
import com.example.typicalweatherapp.data.model.hourly.Hourly;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// TODO clean up model

public class Weather {

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("lon")
    @Expose
    private Double lon;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("timezone_offset")
    @Expose
    private Integer timezoneOffset;

    @SerializedName("current")
    @Expose
    private Current current;

    @SerializedName("hourly")
    @Expose
    private List<Hourly> hourly = null;

    @SerializedName("daily")
    @Expose
    private List<Daily> daily = null;

    @SerializedName("alerts")
    @Expose
    private List<Alert> alerts = null;

    public Weather(
            Double lat,
            Double lon,
            String timezone,
            Integer timezoneOffset,
            Current current,
            List<Hourly> hourly,
            List<Daily> daily,
            List<Alert> alerts
    ) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
        this.alerts = alerts;
    }

    public Weather() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
}
