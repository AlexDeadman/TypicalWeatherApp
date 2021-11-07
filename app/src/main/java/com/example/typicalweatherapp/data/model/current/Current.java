package com.example.typicalweatherapp.data.model.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Current {

    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;

    @SerializedName("sunset")
    @Expose
    private Integer sunset;

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
    private Integer windSpeed;

    @SerializedName("wind_deg")
    @Expose
    private Integer windDeg;

    @SerializedName("weather")
    @Expose
    private List<CurrentWeather> weather = null;

    public Current(
            Integer dt,
            Integer sunrise,
            Integer sunset,
            Double temp,
            Double feelsLike,
            Integer pressure,
            Integer humidity,
            Double dewPoint,
            Integer uvi,
            Integer clouds,
            Integer visibility,
            Integer windSpeed,
            Integer windDeg,
            List<CurrentWeather> weather
    ) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
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
        this.weather = weather;
    }

    public Current() {
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
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

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public List<CurrentWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<CurrentWeather> weather) {
        this.weather = weather;
    }

}
