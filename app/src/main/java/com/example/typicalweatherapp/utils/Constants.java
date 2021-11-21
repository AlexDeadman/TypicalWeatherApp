package com.example.typicalweatherapp.utils;

import com.example.typicalweatherapp.BuildConfig;

public class Constants {

    // Retrofit
    public static final String OWM_BASE_URL = "https://api.openweathermap.org/";
    public static final String OWM_API_KEY = BuildConfig.OWM_API_KEY;
    public static final String GN_BASE_URL = "http://api.geonames.org/";
    public static final String GN_USERNAME = BuildConfig.GN_USERNAME;

    // Preference keys
    public static final String UNITS_TEMP_PREF_KEY = "units_temp";
    public static final String UNITS_SPEED_PREF_KEY = "units_speed";
    public static final String UNITS_PRESSURE_PREF_KEY = "units_pressure";
    public static final String FAVOURITE_CITIES_PREF_KEY = "favourite_cities";
    public static final String CURRENT_CITY_INDEX_PREF_KEY = "current_city_index";
}
