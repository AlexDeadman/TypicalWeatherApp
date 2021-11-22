package com.example.typicalweatherapp.ui.favourite;

import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.repository.FavouritesRepository;

import java.util.List;

import javax.inject.Inject;

public class FavouriteViewModel extends ViewModel {

    @Inject
    public FavouritesRepository favouritesRepository;
    private final List<FavouriteCity> favouriteCities;

    public FavouriteViewModel() {
        App.getAppComponent().inject(this);
        favouriteCities = favouritesRepository.getFavouritesCities();
    }

    void saveFavourites() {
        favouritesRepository.saveFavourites();
    }

    public List<FavouriteCity> getFavouriteCities() {
        return favouriteCities;
    }

    public void setCurrentCity(int position) {
        favouritesRepository.setCurrentCity(position);
    }
}
