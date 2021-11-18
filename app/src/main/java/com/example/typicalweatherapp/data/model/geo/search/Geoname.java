package com.example.typicalweatherapp.data.model.geo.search;

public class Geoname {

    private String lng;
    private Integer geonameId;
    private String name;
    private String countryName;
    private String adminName1;
    private String lat;

    public Geoname(
        String lng,
        Integer geonameId,
        String name,
        String countryName, String adminName1, String lat
    ) {
        this.lng = lng;
        this.geonameId = geonameId;
        this.name = name;
        this.countryName = countryName;
        this.adminName1 = adminName1;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }
}
