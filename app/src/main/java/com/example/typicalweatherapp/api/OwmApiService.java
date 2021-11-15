package com.example.typicalweatherapp.api;

import com.example.typicalweatherapp.data.model.weather.Weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OwmApiService {
    @GET( "data/2.5/onecall?")
    Single<Weather> getWeather(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("exclude") String exclude,
            @Query("units") String units,
            @Query("appid") String apiid
    );
}
