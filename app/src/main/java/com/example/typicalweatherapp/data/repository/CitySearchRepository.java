package com.example.typicalweatherapp.data.repository;

import com.example.typicalweatherapp.api.GnApiService;
import com.example.typicalweatherapp.data.model.geo.search.Geonames;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class CitySearchRepository {

    private final GnApiService apiService;

    @Inject
    public CitySearchRepository(GnApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Geonames> getCities(
        String q,
        int maxRows,
        String lang,
        String username
    ) {
        return apiService.getCities(q, maxRows, lang, username);
    }
}
