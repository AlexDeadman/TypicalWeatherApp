package com.example.typicalweatherapp.utils;

public class Constants {

    // Retrofit
    public static final String OWM_BASE_URL = "https://api.openweathermap.org/";
    public static final String OWM_API_KEY = Secrets.OWM_API_KEY;
    public static final String GN_BASE_URL = "http://api.geonames.org/";
    public static final String GN_USERNAME = Secrets.GN_USERNAME;

    // Preference keys
    public static final String unitsTempPrefKey = "units_temp";
    public static final String unitsSpeedPrefKey = "units_speed";
    public static final String unitsPressurePrefKey = "units_pressure";
    public static final String favouriteCitiesPrefKey = "favourite_cities";
}
