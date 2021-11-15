package com.example.typicalweatherapp.api;

import com.example.typicalweatherapp.data.model.city.search.Geonames;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GnApiService {
    @GET("searchJSON?")
    Single<Geonames> getCities(
        @Query("q") String q,
        @Query("maxRows") int maxRows,
        @Query("lang") String lang,
        @Query("username") String username
    );
}
