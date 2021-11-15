package com.example.typicalweatherapp.di;

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
    @Singleton
    public OwmApiService provideOwmApiService() {
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.OWM_BASE_URL)
            .build()
            .create(OwmApiService.class);
    }

    @Provides
    @Singleton
    public GnApiService provideGnApiService() {
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.GN_BASE_URL)
            .build()
            .create(GnApiService.class);
    }
}
