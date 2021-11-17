package com.example.typicalweatherapp;

import android.app.Application;

import com.example.typicalweatherapp.di.AppComponent;
import com.example.typicalweatherapp.di.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
