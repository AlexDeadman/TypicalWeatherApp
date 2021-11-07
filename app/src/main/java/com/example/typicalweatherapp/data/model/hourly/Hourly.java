package com.example.typicalweatherapp.data.model.hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {

    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("temp")
    @Expose
    private Double temp;

    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;

    @SerializedName("pressure")
    @Expose
    private Integer pressure;

    @SerializedName("humidity")
    @Expose
    private Integer humidity;

    @SerializedName("dew_point")
    @Expose
    private Double dewPoint;

    @SerializedName("uvi")
    @Expose
    private Integer uvi;

    @SerializedName("clouds")
    @Expose
    private Integer clouds;

    @SerializedName("visibility")
    @Expose
    private Integer visibility;

    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;

    @SerializedName("wind_deg")
    @Expose
    private Integer windDeg;

    @SerializedName("wind_gust")
    @Expose
    private Double windGust;

    @SerializedName("weather")
    @Expose
    private List<HourlyWeather> weather = null;

    @SerializedName("pop")
    @Expose
    private Integer pop;

    public Hourly(
            Integer dt,
            Double temp,
            Double feelsLike,
            Integer pressure,
            Integer humidity,
            Double dewPoint,
            Integer uvi,
            Integer clouds,
            Integer visibility,
            Double windSpeed,
            Integer windDeg,
            Double windGust,
            List<HourlyWeather> weather,
            Integer pop
    ) {
        this.dt = dt;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.weather = weather;
        this.pop = pop;
    }

    public Hourly() {
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getUvi() {
        return uvi;
    }

    public void setUvi(Integer uvi) {
        this.uvi = uvi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public Double getWindGust() {
        return windGust;
    }

    public void setWindGust(Double windGust) {
        this.windGust = windGust;
    }

    public List<HourlyWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<HourlyWeather> weather) {
        this.weather = weather;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

}
