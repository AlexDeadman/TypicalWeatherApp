package com.example.typicalweatherapp.data.repository;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.byid.Favourites;
import com.example.typicalweatherapp.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FavouritesRepository {

    private final Favourites favourites;
    private final List<FavouriteCity> favouriteCities;
    private final Gson gson;

    @Inject
    public FavouritesRepository(
        @NonNull Favourites favourites,
        Gson gson
    ) {
        this.favourites = favourites;
        favouriteCities = favourites.getFavouriteCities();
        this.gson = gson;
    }

    public List<FavouriteCity> getFavouritesCities() {
        return favouriteCities;
    }

    public void saveFavourites() {
        SharedPreferences.Editor editor = App.getPreferences().edit();
        String favouriteCitiesJson = gson.toJson(favourites);
        editor.putString(Constants.FAVOURITE_CITIES_PREF_KEY, favouriteCitiesJson);
        editor.apply();
    }

    public FavouriteCity getCurrentCity() {
        int currentCityIndex = App.getPreferences()
            .getInt(Constants.CURRENT_CITY_INDEX_PREF_KEY, -1);

        if (favouriteCities.isEmpty() || currentCityIndex == -1) {
            return new FavouriteCity();
        } else {
            return favouriteCities.get(currentCityIndex);
        }
    }

    public void setCurrentCity(int position) {
        App.getPreferences()
            .edit()
            .putInt(Constants.CURRENT_CITY_INDEX_PREF_KEY, position)
            .apply();
    }
}
