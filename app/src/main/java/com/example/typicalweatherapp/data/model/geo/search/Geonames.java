package com.example.typicalweatherapp.data.model.geo.search;

import java.util.ArrayList;

public class Geonames {
    private ArrayList<Geoname> geonames;

    public Geonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }

    public Geonames() {
    }

    public ArrayList<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }
}
