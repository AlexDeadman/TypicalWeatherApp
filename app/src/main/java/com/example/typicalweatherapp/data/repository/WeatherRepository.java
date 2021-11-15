package com.example.typicalweatherapp.data.repository;

import com.example.typicalweatherapp.api.OwmApiService;
import com.example.typicalweatherapp.data.model.weather.Weather;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class WeatherRepository {

    private final OwmApiService apiService;

    @Inject
    public WeatherRepository(OwmApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Weather> getWeather(
        Double lat,
        Double lon,
        String exclude,
        String units,
        String apiid
    ) {
        return apiService.getWeather(lat, lon, exclude, units, apiid);
    }
}
