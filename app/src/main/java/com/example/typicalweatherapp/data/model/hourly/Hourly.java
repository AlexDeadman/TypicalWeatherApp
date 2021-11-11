package com.example.typicalweatherapp.data.model.hourly;

import com.example.typicalweatherapp.data.model.WeatherInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {

    private Integer dt;

    private Double temp;

    private Integer pressure;

    private Integer humidity;

    @SerializedName("wind_speed")
    private Double windSpeed;

    private List<WeatherInfo> weather = null;

    public Hourly(
        Integer dt,
        Double temp,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        List<WeatherInfo> weather
    ) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weather = weather;
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

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherInfo> weather) {
        this.weather = weather;
    }
}
