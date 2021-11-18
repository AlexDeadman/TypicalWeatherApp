package com.example.typicalweatherapp.api;

import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.search.CitiesSearchResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GnApiService {
    @GET("searchJSON?")
    Single<CitiesSearchResult> getCities(
        @Query("q") String q,
        @Query("maxRows") int maxRows,
        @Query("lang") String lang,
        @Query("username") String username
    );

    @GET("getJSON?")
    Single<FavouriteCity> getCityById(
        @Query("geonameId") int geonameId,
        @Query("username") String username
    );
}
