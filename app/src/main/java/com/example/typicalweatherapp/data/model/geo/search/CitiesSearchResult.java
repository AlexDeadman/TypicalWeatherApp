package com.example.typicalweatherapp.data.model.geo.search;

import java.util.ArrayList;

public class CitiesSearchResult {

    private ArrayList<Geoname> geonames;

    public CitiesSearchResult(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }

    public CitiesSearchResult() {
    }

    public ArrayList<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }
}
