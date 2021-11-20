package com.example.typicalweatherapp.data.model.geo.byid;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class FavouriteCity {

    private String lat;
    private String lng;
    private Integer geonameId;
    private String toponymName;
    private List<AlternateName> alternateNames = null;

    public FavouriteCity(
        String lat,
        String lng,
        Integer geonameId,
        String toponymName, List<AlternateName> alternateNames
    ) {
        this.lat = lat;
        this.lng = lng;
        this.geonameId = geonameId;
        this.toponymName = toponymName;
        this.alternateNames = alternateNames;
    }

    public FavouriteCity() {
    }

    @NonNull
    @Override
    public String toString() {
        return "FavouriteCity{" +
            "lat='" + lat + '\'' +
            ", lng='" + lng + '\'' +
            ", geonameId=" + geonameId +
            ", alternateNames=" + alternateNames +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteCity that = (FavouriteCity) o;
        return geonameId.equals(that.geonameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geonameId);
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

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public List<AlternateName> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<AlternateName> alternateNames) {
        this.alternateNames = alternateNames;
    }
}
