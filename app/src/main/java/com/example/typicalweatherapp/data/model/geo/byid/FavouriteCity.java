package com.example.typicalweatherapp.data.model.geo.byid;

import java.util.List;

public class FavouriteCity {

    private String lat;
    private String lng;
    private Integer geonameId;
    private List<AlternateName> alternateNames = null;

    public FavouriteCity(
        String lat,
        String lng,
        Integer geonameId,
        List<AlternateName> alternateNames
    ) {
        this.lat = lat;
        this.lng = lng;
        this.geonameId = geonameId;
        this.alternateNames = alternateNames;
    }

    public FavouriteCity() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    public List<AlternateName> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<AlternateName> alternateNames) {
        this.alternateNames = alternateNames;
    }
}
