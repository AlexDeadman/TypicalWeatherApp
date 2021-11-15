package com.example.typicalweatherapp.data.model.city.search;

import java.util.List;

public class Geonames {
    private List<Geoname> geonames;

    public Geonames(List<Geoname> geonames) {
        this.geonames = geonames;
    }

    public Geonames() {
    }

    public List<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<Geoname> geonames) {
        this.geonames = geonames;
    }
}
