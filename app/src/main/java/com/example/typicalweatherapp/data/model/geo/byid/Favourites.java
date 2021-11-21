package com.example.typicalweatherapp.data.model.geo.byid;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// TODO check if class with list is necessary for serializing
public class Favourites {
    private List<FavouriteCity> favouriteCities;

    public Favourites(List<FavouriteCity> favouriteCities) {
        this.favouriteCities = favouriteCities;
    }

    public Favourites() {
        favouriteCities = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return "Favourites{" +
            "favouriteCities=" + favouriteCities +
            '}';
    }

    public List<FavouriteCity> getFavouriteCities() {
        return favouriteCities;
    }

    public void setFavouriteCities(List<FavouriteCity> favouriteCities) {
        this.favouriteCities = favouriteCities;
    }
}
