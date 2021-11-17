package com.example.typicalweatherapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.typicalweatherapp.di.AppComponent;
import com.example.typicalweatherapp.di.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();

        Context appContext = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
