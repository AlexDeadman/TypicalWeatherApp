package com.example.typicalweatherapp.di;

import androidx.annotation.NonNull;

import com.example.typicalweatherapp.api.ApiService;
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
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
