package com.example.typicalweatherapp.di;

import androidx.annotation.NonNull;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.api.GnApiService;
import com.example.typicalweatherapp.api.OwmApiService;
import com.example.typicalweatherapp.utils.Constants;

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
    public void provideFavouriteCities() {
        String favouriteCitiesJson = App
            .getPreferences()
            .getString("favourite_cities", "");
    }
}
