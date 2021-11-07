package com.example.typicalweatherapp.data.repository;

import com.example.typicalweatherapp.api.ApiService;
import com.example.typicalweatherapp.data.model.Weather;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class WeatherRepository {

    private final ApiService apiService;

    @Inject
    public WeatherRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Weather> getWeather(
            Double lat,
            Double lon,
            String exclude,
            String apiid
    ) {
        return apiService.getWeather(lat, lon, exclude, apiid);
    }
}
