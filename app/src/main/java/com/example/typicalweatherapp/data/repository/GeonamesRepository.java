package com.example.typicalweatherapp.data.repository;

import com.example.typicalweatherapp.api.GnApiService;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.search.CitySearchResult;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GeonamesRepository {

    private final GnApiService apiService;

    @Inject
    public GeonamesRepository(GnApiService apiService) {
        this.apiService = apiService;
    }

    public Single<CitySearchResult> getCities(
        String q,
        int maxRows,
        String lang,
        String username
    ) {
        return apiService.getCities(q, maxRows, lang, username);
    }

    public Single<FavouriteCity> getCityById(
        int geonameId,
        String username
    ) {
        return apiService.getCityById(geonameId, username);
    }
}
