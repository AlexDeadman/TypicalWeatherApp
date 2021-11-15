package com.example.typicalweatherapp.data.model.city.search;

public class Geoname {
    private String lng;
    private Integer geonameId;
    private String toponymName;
    private String name;
    private String lat;

    public Geoname(
        String lng,
        Integer geonameId,
        String toponymName,
        String name,
        String lat
    ) {
        this.lng = lng;
        this.geonameId = geonameId;
        this.toponymName = toponymName;
        this.name = name;
        this.lat = lat;
    }

    public Geoname() {
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

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
