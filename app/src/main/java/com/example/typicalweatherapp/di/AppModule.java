package com.example.typicalweatherapp.di;

import androidx.annotation.NonNull;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.api.GnApiService;
import com.example.typicalweatherapp.api.OwmApiService;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.byid.Favourites;
import com.example.typicalweatherapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    @Provides
    @Singleton
    public OwmApiService provideOwmApiService(@NonNull Retrofit.Builder builder) {
        return builder
            .baseUrl(Constants.OWM_BASE_URL)
            .build()
            .create(OwmApiService.class);
    }

    @Provides
    @Singleton
    public GnApiService provideGnApiService(@NonNull Retrofit.Builder builder) {
        return builder
            .baseUrl(Constants.GN_BASE_URL)
            .build()
            .create(GnApiService.class);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public Favourites provideFavouriteCities(Gson gson) {
        String favouriteCitiesJson = App
            .getPreferences()
            .getString(Constants.FAVOURITE_CITIES_PREF_KEY, "");

        if (favouriteCitiesJson.isEmpty()) {
            return new Favourites();
        } else {
            return gson.fromJson(favouriteCitiesJson, Favourites.class);
        }
    }
}
