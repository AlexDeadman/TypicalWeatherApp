package com.example.typicalweatherapp.data.model.geo.search;

import java.util.ArrayList;

public class CitySearchResult {

    private ArrayList<Geoname> geonames;

    public CitySearchResult(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }

    public CitySearchResult() {
    }

    public ArrayList<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }
}
