package com.example.typicalweatherapp.data.model;

import com.example.typicalweatherapp.data.model.current.Current;
import com.example.typicalweatherapp.data.model.daily.Daily;
import com.example.typicalweatherapp.data.model.hourly.Hourly;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    private Current current;

    private List<Hourly> hourly = null;

    private ArrayList<Daily> daily = null;

    public Weather(
        Current current,
        List<Hourly> hourly,
        ArrayList<Daily> daily
    ) {
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
    }

    public Weather() {
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

    public ArrayList<Daily> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<Daily> daily) {
        this.daily = daily;
    }
}
