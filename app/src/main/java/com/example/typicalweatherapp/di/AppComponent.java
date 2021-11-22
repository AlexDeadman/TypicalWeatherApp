package com.example.typicalweatherapp.di;

import com.example.typicalweatherapp.ui.addcity.AddCityViewModel;
import com.example.typicalweatherapp.ui.favourite.FavouriteViewModel;
import com.example.typicalweatherapp.ui.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainViewModel mainViewModel);
    void inject(AddCityViewModel addCityViewModel);
    void inject(FavouriteViewModel favouriteViewModel);
}
