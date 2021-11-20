package com.example.typicalweatherapp.di;

import com.example.typicalweatherapp.ui.addcity.AddCityActivity;
import com.example.typicalweatherapp.ui.addcity.AddCityViewModel;
import com.example.typicalweatherapp.ui.favourite.FavouriteActivity;
import com.example.typicalweatherapp.ui.main.MainActivity;
import com.example.typicalweatherapp.ui.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(MainViewModel mainViewModel);

    void inject(AddCityViewModel addCityViewModel);
    void inject(AddCityActivity addCityActivity);

    void inject(FavouriteActivity favouriteActivity);
}
